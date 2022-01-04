package com.min.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.min", "com.min.common"})
public class ServiceOssApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(ServiceOssApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
