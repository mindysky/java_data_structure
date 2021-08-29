package com.min.mybatisdemo.service.impl;

import com.min.mybatisdemo.entity.User;
import com.min.mybatisdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    public void testCount(){
        int count = userService.count();
        System.out.println("count=="+ count);
    }

    @Test
    public void testSaveBatch(){
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("lucy"+i);
            user.setAge(30+i);

            users.add(user);
        }
        boolean b = userService.saveBatch(users);
        System.out.println("isSuccess==="+b);

    }

}