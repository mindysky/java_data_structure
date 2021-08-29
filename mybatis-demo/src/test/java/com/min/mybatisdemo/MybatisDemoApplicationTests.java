package com.min.mybatisdemo;

import com.min.mybatisdemo.entity.User;
import com.min.mybatisdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest  //自动创建spring 上下文环境
class MybatisDemoApplicationTests {

//    @Autowired  //Spring
    @Resource  //J2EE
    private UserMapper userMapper;


    @Test
    void testSelectList() {
        List<User> users = userMapper.selectList(null);

        users.forEach(System.out::println);

    }

}
