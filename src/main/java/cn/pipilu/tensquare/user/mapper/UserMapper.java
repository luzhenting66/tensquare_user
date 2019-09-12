package cn.pipilu.tensquare.user.mapper;

import cn.pipilu.tensquare.user.entity.UserEntity;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends BaseMapper<UserEntity> ,Mapper<UserEntity>{
}
