package cn.pipilu.tensquare.user.request;

import cn.pipilu.plus.common.validate.Validate;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString
public class UserRegisterReq implements Serializable {

    private static final long serialVersionUID = -8031556136633249210L;
    @Validate(nullable = false,desc = "手机号")
    private String mobile;
    @Validate(nullable = false,desc = "密码")
    private String password;
    //private String code;
}
