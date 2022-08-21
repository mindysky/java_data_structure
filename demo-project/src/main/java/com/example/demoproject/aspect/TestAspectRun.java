package com.example.demoproject.aspect;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TestAspectRun {
    @Pointcut("execution(public void com.example..demoproject.demo.Runner.*(..))")
    public void myPointcut() {
    }
    @Around("myPointcut()")
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
