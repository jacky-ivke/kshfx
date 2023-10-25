package com.kshfx.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LogAop extends AbstractBaseAop {

    private final String[] ignoreRequest = new String[]{"HEAD", "OPTIONS", "TRACE"};

    private final Logger requestLogger = LoggerFactory.getLogger("internalApi");

    private final Logger serviceLogger = LoggerFactory.getLogger("externalApi");

    @Pointcut(" execution(* com.kshfx..*.controller..*.*(..))")
    public void pointcutRequest() {
    }

    @Around("pointcutRequest()")
    public Object aroundRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        long startTime = System.currentTimeMillis();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (Arrays.asList(ignoreRequest).contains(request.getMethod())) {
            return null;
        }
        try {
            result = joinPoint.proceed();
        } finally {
            long runTime = System.currentTimeMillis() - startTime;
            this.log(request, requestLogger, joinPoint, result, runTime);
        }
        return result;
    }
}