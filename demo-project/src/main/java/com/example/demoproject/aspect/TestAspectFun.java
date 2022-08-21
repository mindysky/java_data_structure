package com.example.demoproject.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

@Aspect
@Component
@Slf4j
public class TestAspectFun {
    @Pointcut("execution(* com.example..demoproject.demo.DemoFilter.*(..))")
    public void smsPointcut() {
    }

//    @Pointcut("execution(* com.example..demoproject.controller.HelloController.*(..))")
//    public void smsPointcut() {
//    }
    @Around("smsPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        String s = proceedingJoinPoint.getTarget().getClass().toString();
        log.info("className {}",s);

        // 执行耗时
        log.info("花费时间: {} ms", System.currentTimeMillis() - startTime);
        // 打印出参
        log.info("返回结果: {}", JSON.toJSONString(result));
        log.info("=========================================== End ===========================================");
        return result;
    }


}
