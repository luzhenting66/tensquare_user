package cn.pipilu.tensquare.user.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserLoginResp implements Serializable {

    private static final long serialVersionUID = 1802186932606228396L;
    private String nickname;
    private String userId;
    private String token;
    private String roles;
}
