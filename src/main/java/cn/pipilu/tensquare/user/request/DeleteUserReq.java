package cn.pipilu.tensquare.user.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString
public class DeleteUserReq implements Serializable {
    private String id;
}
