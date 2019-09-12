package cn.pipilu.tensquare.user.entity;

import cn.pipilu.plus.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Table(name = "tb_user")
public class UserEntity extends BaseEntity {

    private String mobile;//手机号码',
    private String password;//'密码',
    @Column(name = "nickname")
    private String nickName;//'昵称',
    private String sex;// '性别',
    private Date birthday;//出生年月日',
    private String avatar;// '头像',
    private String email;//'E-Mail',
    private Date regdate;//'注册日期',
    @Column(name = "updatedate")
    private Date updateDate;//'修改日期',
    @Column(name = "lastdate")
    private Date lastDate;//'最后登陆日期',
    private Integer online;//'在线时长（分钟）',
    private String interest;// '兴趣',
    private String personality;//'个性',
    @Column(name = "fanscount")
    private Integer fansCount;// '粉丝数',
    @Column(name = "followcount")
    private Integer followCount;//'关注数',
    private String state; //0-删除 、1-可用
}
