package net.springbootintellij;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* net.springbootintellij.*Driver.*(..))")
    private void driverClass() {

    }

    @Before("driverClass()")
    public void logBefore() {
        log.info("[AOP 로그 before] 메서드 실행 전에 처리할 코드를 작성합니다.");
    }


    @After("driverClass()")
    public void logAfter() {
        log.info("[AOP 로그 after] 메서드 실행 후에 처리할 코드를 작성합니다.");
    }

    @Around("driverClass()")
    public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("[AOP 로그 Around] 메서드 실행 전에 처리할 코드를 작성합니다.");
        joinPoint.proceed();
        log.debug("[AOP 로그 Around] 메서드 실행 후에 처리할 코드를 작성합니다.");
    }
}
