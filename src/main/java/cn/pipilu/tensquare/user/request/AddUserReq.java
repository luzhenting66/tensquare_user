package cn.pipilu.tensquare.user.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AddUserReq implements Serializable {

    private static final long serialVersionUID = -5020005158745140103L;
    private String mobile;//手机号码',
    private String password;//'密码',
    private String nickName;//'昵称',
    private String sex;// '性别',
    private Date birthday;//出生年月日',
    private String avatar;// '头像',
    private String email;//'E-Mail',
}
