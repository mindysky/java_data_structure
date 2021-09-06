package com.min.mybatisdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.min.mybatisdemo.entity.User;
import com.min.mybatisdemo.mapper.UserMapper;
import com.min.mybatisdemo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<User> listAllByName(String name) {
        return baseMapper.selectAllByName("Jone");
    }
}
