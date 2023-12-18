package com.whh.curator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whh.curator.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
