package com.min.mybatisdemo;

import com.min.mybatisdemo.entity.User;
import com.min.mybatisdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class MapperTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testInsert(){
        User user = new User();
        user.setName("tom9999");
        user.setAge(70);
        user.setEmail("tom@qq.com");
        int result = userMapper.insert(user);
        System.out.println("==="+result);
    }
    @Test
    public void testSelect(){
        User user = userMapper.selectById(1);
        System.out.println(user);

        List<User> users = userMapper.selectBatchIds(Arrays.asList(1,2,3));
        users.forEach(System.out::println);

        HashMap<String, Object> map = new HashMap<>();
        map.put("name","tom");
        map.put("age",70);

        List<User> users1 = userMapper.selectByMap(map);
        users1.forEach(System.out::println);
    }
    
    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(1L);
        user.setAge(90);
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    @Test
    public void testDelete(){
        int i = userMapper.deleteById(2L);
        System.out.println(i);
    }

    @Test
    public void selectByName(){
        List<User> users = userMapper.selectAllByName("tom");
        users.forEach(System.out::println);
    }

}
