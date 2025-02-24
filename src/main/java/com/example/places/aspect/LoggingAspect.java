package com.example.places.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.example.places.controller.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("REQUEST - Method: {} - Args: {}", 
            joinPoint.getSignature().getName(), 
            joinPoint.getArgs());
        
        Object result = joinPoint.proceed();
        
        logger.info("RESPONSE - Method: {} - Response: {}", 
            joinPoint.getSignature().getName(), 
            result);
            
        return result;
    }
}