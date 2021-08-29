package com.min.mybatisdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.min.mybatisdemo.entity.User;
import com.min.mybatisdemo.mapper.UserMapper;
import com.min.mybatisdemo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
