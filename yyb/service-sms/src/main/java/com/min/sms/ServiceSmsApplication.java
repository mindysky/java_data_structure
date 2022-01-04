package com.min.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.min", "com.min.common"})
@EnableFeignClients
public class ServiceSmsApplication {
    public static void main(String[] args) {
        System.out.println("ss");
        try {
            SpringApplication.run(ServiceSmsApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ss6");
    }
}
