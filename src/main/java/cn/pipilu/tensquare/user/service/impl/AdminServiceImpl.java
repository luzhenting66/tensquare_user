package cn.pipilu.tensquare.user.service.impl;

import cn.pipilu.plus.common.exception.AppException;
import cn.pipilu.plus.common.util.IdUtil;
import cn.pipilu.plus.common.util.JwtUtil;
import cn.pipilu.plus.common.util.RedisUtil;
import cn.pipilu.plus.common.util.ValidateUtil;
import cn.pipilu.tensquare.user.entity.AdminEntity;
import cn.pipilu.tensquare.user.mapper.AdminMapper;
import cn.pipilu.tensquare.user.request.AddAdminReq;
import cn.pipilu.tensquare.user.request.AdminLoginReq;
import cn.pipilu.tensquare.user.response.AdminLoginResp;
import cn.pipilu.tensquare.user.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private IdUtil idUtil;
    @Autowired
    private ValidateUtil validateUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public void add(AddAdminReq reqData) {
        validateUtil.validObject(reqData);
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setLoginName(reqData.getLoginName().trim());
        adminEntity.setId(idUtil.getIdStr());
        adminEntity.setState("0");
        adminEntity.setPassword(encoder.encode(reqData.getPassword().trim()));
        adminMapper.insert(adminEntity);
    }

    @Override
    public AdminLoginResp login(AdminLoginReq reqData) {
        validateUtil.validObject(reqData);
        Example example = new Example(AdminEntity.class);
        example.createCriteria().andEqualTo("loginName",reqData.getLoginName().trim())
                .andEqualTo("state","1");

        List<AdminEntity> admins = adminMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(admins) || admins.size() > 1){
            throw new AppException("用户名密码信息不匹配");
        }
        AdminEntity admin = admins.get(0);
        if (!encoder.matches(reqData.getPassword(),admin.getPassword())){
            throw new AppException("用户名密码信息不匹配");
        }
        AdminLoginResp resp = new AdminLoginResp();
        resp.setAdminId(admin.getId());
        resp.setLoginName(admin.getLoginName());
        resp.setRoles("admin");
        resp.setToken(jwtUtil.createJWT(admin.getId(), admin.getLoginName(), "admin"));
        return resp;
    }
}
