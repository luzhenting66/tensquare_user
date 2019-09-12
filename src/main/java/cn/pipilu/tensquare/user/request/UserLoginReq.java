package cn.pipilu.tensquare.user.request;

import cn.pipilu.plus.common.validate.Validate;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserLoginReq implements Serializable {
    @Validate(nullable = false,desc = "昵称")
    private String nickname;//'登陆名称',
    @Validate(nullable = false,desc = "密码")
    private String password;//密码',
}
