package cn.pipilu.tensquare.user.request;

import cn.pipilu.plus.common.validate.Validate;
import lombok.Data;

import java.io.Serializable;
@Data
public class AddAdminReq implements Serializable {

    private static final long serialVersionUID = -8167644135306904922L;
    @Validate(nullable = false,desc = "登录名缺失")
    private String loginName;//'登陆名称',
    @Validate(nullable = false,desc = "密码缺失")
    private String password;//密码',
    private String state;//'状态',
}
