package net.likelion.bebc25.spring.componentscan.aop.springaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class LoggingAspect {

    @Pointcut("execution(* net.likelion.bebc25.spring.aop.springaop.*Car.*(..))")
    private void springAopPackageMethod() {

    }

    @Before("execution(* net.likelion.bebc25.spring.aop.springaop.*Driver.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[AOP 로그 before] 메서드 실행 전에 처리할 코드를 작성합니다.");
        Object[] args = joinPoint.getArgs();
        System.out.println(Arrays.toString(args));
    }


    @After("execution(* net.likelion.bebc25.spring.aop.springaop.*Driver.*(..))")
    public void logAfter() {
        System.out.println("[AOP 로그 after] 메서드 실행 후에 처리할 코드를 작성합니다.");
    }

    @Around("springAopPackageMethod()")
    public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("[AOP 로그 Around] 메서드 실행 전에 처리할 코드를 작성합니다.");
        joinPoint.proceed();
        System.out.println("[AOP 로그 Around] 메서드 실행 후에 처리할 코드를 작성합니다.");
    }
}
