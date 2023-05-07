package com.book.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimeCheckAspect {

    @Around("execution(* com.book.controller..*(..))")
    public Object checkTime(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        try {
            Object proceed = joinPoint.proceed();
            long timeTaken = System.currentTimeMillis() - startTime;
            log.info("[method] = " + joinPoint.getSignature());
            log.info("[time taken] = " + timeTaken);
            return proceed;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
