package ru.t1.logging.aspect;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Arrays;

@Slf4j
@Aspect
@Setter
public class LoggingAspect {

    private String logLevel = "info";

    @Before("@annotation(ru.t1.logging.aspect.annotation.LogBefore)")
    public void logBefore(JoinPoint joinPoint) {
        log("Starting method {}. Arguments: {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "@annotation(ru.t1.logging.aspect.annotation.LogAfterReturning)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log("Finish method {}. Result: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(value = "@annotation(ru.t1.logging.aspect.annotation.LogAfterThrowing)", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log("Exception in {} with args={} → {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()),
                ex.toString(), ex);
    }

    @Around("@annotation(ru.t1.logging.aspect.annotation.LogAround)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log("Around start in method {}.", joinPoint.getSignature().getName());
        long startTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            log("Around finish.  Time taken: {} ms", endTime - startTime);
        }
    }

    private void log(String message, Object... args) {
        switch (logLevel) {
            case "debug":
                if (log.isDebugEnabled()) log.debug(message, args);
                break;
            case "warn":
                if (log.isWarnEnabled()) log.warn(message, args);
                break;
            case "error":
                if (log.isErrorEnabled()) log.error(message, args);
                break;
            default:
                if (log.isInfoEnabled()) log.info(message, args);
        }
    }
}
