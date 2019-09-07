package cn.pipilu.tensquare.user.controller;

import cn.pipilu.plus.common.request.Request;
import cn.pipilu.plus.common.response.Response;
import cn.pipilu.plus.common.util.ResponseUtil;
import cn.pipilu.tensquare.user.request.AddAdminReq;
import cn.pipilu.tensquare.user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/tensquare-user/admin")
@RestController
public class AdminController {
@Autowired
private AdminService adminService;
    @RequestMapping("/save")
    public Response<Void> save(@RequestBody Request<AddAdminReq> reqData){
        Response<Void> response = new Response<>();
        try {
            adminService.save(reqData.getReqData());
            ResponseUtil.setRespParam(response);
        } catch (Exception e) {
            ResponseUtil.setRespParam(response, e);
        }
        return response;
    }
}
