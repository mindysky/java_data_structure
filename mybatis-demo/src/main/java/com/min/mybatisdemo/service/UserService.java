package com.min.mybatisdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.min.mybatisdemo.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> listAllByName(String name);
}
