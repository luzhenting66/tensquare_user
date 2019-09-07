package cn.pipilu.tensquare.user.controller;

import cn.pipilu.plus.common.request.Request;
import cn.pipilu.plus.common.response.Response;
import cn.pipilu.plus.common.util.ResponseUtil;
import cn.pipilu.tensquare.user.request.AddUserReq;
import cn.pipilu.tensquare.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tensquare-user/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/save")
    public Response<Void> save(@RequestBody Request<AddUserReq> reqData) {
        Response<Void> response = new Response<>();
        try {
            userService.save(reqData.getReqData());
            ResponseUtil.setRespParam(response);
        } catch (Exception e) {
            ResponseUtil.setRespParam(response, e);
        }
        return response;
    }

}
