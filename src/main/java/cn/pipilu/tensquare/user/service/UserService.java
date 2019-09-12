package cn.pipilu.tensquare.user.service;

import cn.pipilu.tensquare.user.request.AddUserReq;
import cn.pipilu.tensquare.user.request.SendSmsReq;
import cn.pipilu.tensquare.user.request.UserLoginReq;
import cn.pipilu.tensquare.user.request.UserRegisterReq;
import cn.pipilu.tensquare.user.response.SendSmsResp;
import cn.pipilu.tensquare.user.response.UserLoginResp;
import cn.pipilu.tensquare.user.response.UserRegisterResp;

public interface UserService {
    void add(AddUserReq reqData);
    //发送验证
    void sendsms(SendSmsReq reqData);

    UserRegisterResp register(String code, UserRegisterReq reqData);

    UserLoginResp login(UserLoginReq reqData);

    void delete(String id);
}
