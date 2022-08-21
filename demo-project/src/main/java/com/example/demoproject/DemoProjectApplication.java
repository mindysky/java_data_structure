package com.example.demoproject;

import com.example.demoproject.config.AspectConfig;
import com.example.demoproject.demo.DemoFilter;
import com.example.demoproject.demo.GetDatefromJson;
import com.example.demoproject.demo.Runner;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.IOException;

@SpringBootApplication
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoProjectApplication {

    public static void main(String[] args) throws IOException, JSONException {

        SpringApplication.run(DemoProjectApplication.class, args);
        GetDatefromJson getDatefromJson = new GetDatefromJson();
        getDatefromJson.getMap();

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AspectConfig.class);
        DemoFilter bean = annotationConfigApplicationContext.getBean(DemoFilter.class);
        bean.add("aa");


    }

}
