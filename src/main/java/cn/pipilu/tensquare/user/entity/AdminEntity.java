package cn.pipilu.tensquare.user.entity;

import cn.pipilu.plus.common.base.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "tb_admin")
public class AdminEntity extends BaseEntity {

    private static final long serialVersionUID = -3107305209398412481L;
    @Column(name = "loginname")
    private String loginName;//'登陆名称',
    private String password;//密码',
    private String state;//'状态',
}
