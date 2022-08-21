package com.example.demoproject.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

public class GetDatefromJson {

    private Map<String, Boolean> transferToMap(TestOjects item) {
        return item.getItemList().stream().collect(Collectors.toMap(TestOjects.Item::getItemID, TestOjects.Item::getItemLive));
    }

    public void getMap() throws IOException {

        Resource res = new ClassPathResource("test.json");
        InputStream input = res.getInputStream();
        byte data[] = new byte[1024]; // 开辟一个缓冲区读取数据
        int len = input.read(data); // 读取数据
        String str = new String(data, 0, len);
        input.close(); // 手动关闭资源

        ObjectMapper mapper = new ObjectMapper();
        List<TestOjects> objects = mapper.readValue(str, new TypeReference<List<TestOjects>>() {
        });

        Map<String, Map<String, Boolean>> collect1 = objects.stream().filter(o -> o.getSystem().equals("GGG") || o.getSystem().equals("BBB")).collect(Collectors.toMap(TestOjects::getSystem,
                item -> item.getItemList().stream().collect(Collectors.toMap(TestOjects.Item::getItemID, TestOjects.Item::getItemLive))));


        System.out.println(collect1);

    }
}