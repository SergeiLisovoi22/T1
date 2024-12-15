package com.example.aspect;

import com.example.web.controller.model.ResponseTaskDTO;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class MainAspect {
    private static final Logger logger = LoggerFactory.getLogger(MainAspect.class.getName());

    @Before("@annotation(com.example.aspect.annotation.LogExecution)")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before calling method {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "@annotation(com.example.aspect.annotation.LogException)")
    public void logAfterThrowing(JoinPoint joinPoint) {
        logger.error("Exception throwing in method {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(
            pointcut = "@annotation(com.example.aspect.annotation.HandlingResult)",
            returning = "result"
    )
    public void handleResult(JoinPoint joinPoint, ResponseTaskDTO result) {
        logger.info("Handling result in method {}", joinPoint.getSignature().getName());
        if (result != null) {
            logger.info("More info: {}", result);
        }
    }

    @Around("@annotation(com.example.aspect.annotation.LogTracking)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Around START calling method {}", methodName);
        Object proceeded;
        try {
            proceeded = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException("Around EXCEPTION", e);
        }
        logger.info("Around FINISH calling method {}", methodName);
        return proceeded;
    }
}
