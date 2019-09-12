package cn.pipilu.tensquare.user.service.impl;

import cn.pipilu.plus.common.exception.AppException;
import cn.pipilu.plus.common.util.IdUtil;
import cn.pipilu.plus.common.util.JwtUtil;
import cn.pipilu.plus.common.util.RedisUtil;
import cn.pipilu.plus.common.util.ValidateUtil;
import cn.pipilu.tensquare.user.entity.UserEntity;
import cn.pipilu.tensquare.user.mapper.UserMapper;
import cn.pipilu.tensquare.user.request.AddUserReq;
import cn.pipilu.tensquare.user.request.SendSmsReq;
import cn.pipilu.tensquare.user.request.UserLoginReq;
import cn.pipilu.tensquare.user.request.UserRegisterReq;
import cn.pipilu.tensquare.user.response.SendSmsResp;
import cn.pipilu.tensquare.user.response.UserLoginResp;
import cn.pipilu.tensquare.user.response.UserRegisterResp;
import cn.pipilu.tensquare.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IdUtil idUtil;
    @Autowired
    private ValidateUtil validateUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtil;


    public static final String CHECK_CODE_PRE="check:code:";
    public static final String TOKEN_PRE="token:";
    @Value("${spring.rabbitmq.sms.exchange.name}")
    private String smsExchangeName;
    @Value("${spring.rabbitmq.sms.routing.key.name}")
    private String smsRoutingKeyName;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpServletRequest request;
    @Override
    public void add(AddUserReq addUserReq) {
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(addUserReq, user);
        user.setId(idUtil.getIdStr());
        user.setState("1");
        user.setPassword(encoder.encode(user.getPassword().trim()));
        userMapper.insert(user);

    }

    @Override
    public void sendsms(SendSmsReq reqData) {
        validateUtil.validObject(reqData);
        String code = getSixCode();
        String mobile = reqData.getMobile().trim();
        redisUtil.set(CHECK_CODE_PRE+ mobile,code,3600);

        //mq 里存一份，异步发送消息
        System.err.println(mobile+"，验证码："+code);
        SendSmsResp sendSmsResp = new SendSmsResp();
        sendSmsResp.setCode(code);
        sendSmsResp.setMobile(mobile);
       // rabbitmqSendData(smsExchangeName,smsRoutingKeyName,sendSmsResp);
    }

    private void rabbitmqSendData(String exchange,String routingKey,Object data){
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setRoutingKey(routingKey);
        Message message= null;
        try {
            message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(data)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
        rabbitTemplate.convertAndSend(message);

    }

    @Override
    public UserRegisterResp register(String code, UserRegisterReq reqData) {
        validateUtil.validObject(reqData);
        String mobile = reqData.getMobile().trim();
        String redisCode = redisUtil.get(CHECK_CODE_PRE + mobile);
        if (StringUtils.isBlank(redisCode) || !redisCode.equals(code)){
            throw new AppException("验证码信息错误");
        }
        String id = idUtil.getIdStr();
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setNickName(mobile);
        user.setMobile(mobile);
        user.setPassword(encoder.encode(reqData.getPassword().trim()));
        user.setSex("男");
        userMapper.insert(user);
        UserRegisterResp resp = new UserRegisterResp();
        resp.setNickName(user.getNickName());
        resp.setUserId(id);
        resp.setToken("user_1111111111111_"+id);
        redisUtil.set(TOKEN_PRE+id,resp.getToken(),3600);
        redisUtil.del(CHECK_CODE_PRE + mobile);
        return resp;
    }

    @Override
    public UserLoginResp login(UserLoginReq reqData) {
        validateUtil.validObject(reqData);
        Example example = new Example(UserEntity.class);
        example.createCriteria().andEqualTo("nickName",reqData.getNickname().trim());

        List<UserEntity> users = userMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(users) || users.size() > 1){
            throw new AppException("用户名密码信息不匹配");
        }
        UserEntity user = users.get(0);
        if (!encoder.matches(reqData.getPassword(),user.getPassword())){
            throw new AppException("用户名密码信息不匹配");
        }
        UserLoginResp resp = new UserLoginResp();
        resp.setUserId(user.getId());
        resp.setNickname(user.getNickName());
        resp.setRoles("user");
        resp.setToken(jwtUtil.createJWT(user.getId(),user.getNickName(),"user"));
        return resp;
    }

    @Override
    public void delete(String id) {
        String token = (String) request.getAttribute("claims_admin");
        if (StringUtils.isBlank(token)){
            throw new AppException("1006","权限不足");
        }
        Example example = new Example(UserEntity.class);
        example.createCriteria().andEqualTo("id",id);

        UserEntity data = new UserEntity();
        data.setState("0");
        userMapper.updateByExampleSelective(data,example);
    }

    private String getSixCode(){
        return RandomStringUtils.randomNumeric(6);
    }
}
