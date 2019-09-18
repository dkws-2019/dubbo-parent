package com.liuchao.dubbomapper.service;

import com.liuchao.dubbodomain.domain.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    public List<User> findByUserName(@Param("userName")String userName);
}
