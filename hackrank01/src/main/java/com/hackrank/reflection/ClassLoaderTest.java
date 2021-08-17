package com.hackrank.reflection;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClassLoaderTest {

    @Test
    public void test1() throws IOException {
        Properties pros = new Properties();
        //读取配置文件的方式一
        FileInputStream fis = new FileInputStream("src/main/java/com/hackrank/reflection/jdbc.properties");
        pros.load(fis);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");

        System.out.println(user+"==="+password);

    }

    @Test
    public void test2() throws IOException {
        Properties pros = new Properties();
        //读取配置文件的方式二
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("/jdbc.properties");
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");

        System.out.println(user+"===--===="+password);

    }
}
