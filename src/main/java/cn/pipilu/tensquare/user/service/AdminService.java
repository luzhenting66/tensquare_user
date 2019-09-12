package cn.pipilu.tensquare.user.service;

import cn.pipilu.tensquare.user.request.AddAdminReq;
import cn.pipilu.tensquare.user.request.AdminLoginReq;
import cn.pipilu.tensquare.user.response.AdminLoginResp;

public interface AdminService {
    void add(AddAdminReq reqData);

    AdminLoginResp login(AdminLoginReq reqData);
}
