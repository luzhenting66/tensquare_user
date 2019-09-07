package cn.pipilu.tensquare.user.service.impl;

import cn.pipilu.plus.common.util.IdUtils;
import cn.pipilu.plus.common.util.RedisUtil;
import cn.pipilu.plus.common.util.ValidateUtil;
import cn.pipilu.tensquare.user.entity.AdminEntity;
import cn.pipilu.tensquare.user.mapper.AdminMapper;
import cn.pipilu.tensquare.user.request.AddAdminReq;
import cn.pipilu.tensquare.user.service.AdminService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private IdUtils idUtils;
    @Autowired
    private ValidateUtil validateUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void save(AddAdminReq reqData) {
        validateUtil.validObject(reqData);
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setLoginName(reqData.getLoginName().trim());
        adminEntity.setId(idUtils.getIdStr());
        adminEntity.setState("0");
        adminMapper.insert(adminEntity);
    }
}
