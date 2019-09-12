package cn.pipilu.tensquare.user.request;

import cn.pipilu.plus.common.validate.Validate;
import lombok.Data;

import java.io.Serializable;
@Data
public class SendSmsReq implements Serializable {

    private static final long serialVersionUID = -5448510494408274961L;
    @Validate(nullable = false,desc = "手机号")
    private String mobile;
}
