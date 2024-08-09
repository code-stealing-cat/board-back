package com.jm.board_back.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class Aop {
    @Around("@within(com.jm.board_back.customAnnotation.TimeTraceAnnotation)")
    public Object timeTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        log.info("START: {}", joinPoint.getSignature());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("END: {} {}ms", joinPoint.getSignature(), timeMs);
        }
    }

    /*
    @Around("@within(com.jm.board_back.customAnnotation.TimeTraceAnnotation)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("실행위치 : {}    실행한 메소드 @@ : {}",joinPoint.getSignature(), methodName);

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long elapsedTime = System.currentTimeMillis() - start;
        log.info("실행위치 : {}   메소드 !!! : {} 실행시간?? {}ms", joinPoint.getSignature(), methodName, elapsedTime);

        return result;
    }
     */

}
