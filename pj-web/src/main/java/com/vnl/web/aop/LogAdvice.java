package com.vnl.web.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * ログAOPクラスです。
 */
@Component
@Aspect
@Slf4j
public class LogAdvice {

    @Before("within(com.vnl.web.usecase.*)")
    public static void invokeBeforeUseCase(final JoinPoint joinPoint) {
        methodLog(joinPoint.getTarget().getClass().toString(), joinPoint.getSignature().getName(), "start");
    }

    @After("within(com.vnl.web.usecase.*)")
    public static void invokeAfterUseCase(final JoinPoint joinPoint) {
        methodLog(joinPoint.getTarget().getClass().toString(), joinPoint.getSignature().getName(), "end");
    }

    private static void methodLog(final String className, final String methodName, final String message) {
        log.info("{}.{}() {}.", className, methodName, message);
    }

}
