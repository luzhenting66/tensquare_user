package cn.pipilu.tensquare.user.service.impl;

import cn.pipilu.plus.common.util.IdUtils;
import cn.pipilu.tensquare.user.entity.UserEntity;
import cn.pipilu.tensquare.user.mapper.UserMapper;
import cn.pipilu.tensquare.user.request.AddUserReq;
import cn.pipilu.tensquare.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IdUtils idUtils;
    @Override
    public void save(AddUserReq addUserReq) {
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(addUserReq, user);
        user.setId(idUtils.getIdStr());
        userMapper.insert(user);

    }
}
