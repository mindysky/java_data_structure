package com.example.demoproject.config;

import com.example.demoproject.aspect.TestAspectFun;
import com.example.demoproject.aspect.TestAspectRun;
import com.example.demoproject.demo.DemoFilter;
import com.example.demoproject.demo.Runner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
public class AspectConfig {
//    @Bean
//    public Runner runner() {
//        return new Runner();
//    }

    @Bean
    public TestAspectRun runAspect() {
        return new TestAspectRun();
    }

    @Bean
    public DemoFilter demoFilter(){
        return new DemoFilter();
    }

    @Bean
    public TestAspectFun funAspect() {
        return new TestAspectFun();
    }
}
