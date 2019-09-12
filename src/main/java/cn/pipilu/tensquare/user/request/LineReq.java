package cn.pipilu.tensquare.user.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LineReq {

    private int orderId;
    private double xCoord;
    private double yCoord;
    private double demand;
    private double readyTime;
    private double dueTime;
    private double serviceTime;
}
