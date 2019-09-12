package cn.pipilu.tensquare.user.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString
public class UserRegisterResp implements Serializable {

    private static final long serialVersionUID = -6143266551376475465L;
    private String token;
    private String nickName;
    private String userId;
}
