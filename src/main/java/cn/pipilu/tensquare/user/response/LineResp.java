package cn.pipilu.tensquare.user.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;
@Data
@ToString
public class LineResp {
    List<LineSortVO> data;
}
