package cn.pipilu.tensquare.user.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString
public class AdminLoginResp implements Serializable {

    private static final long serialVersionUID = 1802186932606228396L;
    private String loginName;
    private String adminId;
    private String token;
    private String roles;
}
