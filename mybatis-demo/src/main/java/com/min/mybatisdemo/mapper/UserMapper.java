package com.min.mybatisdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.min.mybatisdemo.entity.User;

import java.util.List;

//@Repository
public interface UserMapper extends BaseMapper<User> {
    List<User> selectAllByName(String name);
}
