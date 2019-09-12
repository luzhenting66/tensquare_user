package cn.pipilu.tensquare.user.controller;

import cn.pipilu.plus.common.request.Request;
import cn.pipilu.plus.common.response.Response;
import cn.pipilu.plus.common.util.ResponseUtil;
import cn.pipilu.tensquare.user.request.*;
import cn.pipilu.tensquare.user.response.AdminLoginResp;
import cn.pipilu.tensquare.user.response.UserLoginResp;
import cn.pipilu.tensquare.user.response.UserRegisterResp;
import cn.pipilu.tensquare.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tensquare-user/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Response<Void> add(@RequestBody Request<AddUserReq> reqData) {
        Response<Void> response = new Response<>();
        try {
            userService.add(reqData.getReqData());
            ResponseUtil.setRespParam(response);
        } catch (Exception e) {
            ResponseUtil.setRespParam(response, e);
        }
        return response;
    }

    //发送验证码
    @RequestMapping(value = "/sendsms",method = RequestMethod.POST)
    public Response<Void> sendsms(@RequestBody Request<SendSmsReq> reqData){
        Response<Void> response = new Response<>();
        try {
            userService.sendsms(reqData.getReqData());
            ResponseUtil.setRespParam(response);
        } catch (Exception e) {
            ResponseUtil.setRespParam(response, e);
        }
        return response;
    }

    //发送验证码
    @RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
    public Response<UserRegisterResp> register(@PathVariable String code, @RequestBody Request<UserRegisterReq> reqData){
        Response<UserRegisterResp> response = new Response<>();
        try {
            response.setRespData(userService.register(code,reqData.getReqData()));
            ResponseUtil.setRespParam(response);
        } catch (Exception e) {
            ResponseUtil.setRespParam(response, e);
        }
        return response;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Response<UserLoginResp> login(@RequestBody Request<UserLoginReq> reqData){
        Response<UserLoginResp> response = new Response<>();
        try {
            response.setRespData(userService.login(reqData.getReqData()));
            ResponseUtil.setRespParam(response);
        } catch (Exception e) {
            ResponseUtil.setRespParam(response, e);
        }
        return response;
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Response<Void> delete(@PathVariable String id){
        Response<Void> response = new Response<>();
        try {
            userService.delete(id);
            ResponseUtil.setRespParam(response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtil.setRespParam(response, e);
        }
        return response;
    }
}
