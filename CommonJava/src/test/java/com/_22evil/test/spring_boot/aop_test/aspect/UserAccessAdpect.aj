package com._22evil.test.spring_boot.aop_test.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class UserAccessAdpect {

    @Before("execution(* com._22evil.test.spring_boot.aop_test.service.Business1.calculateSomething())")
    public void before(JoinPoint joinPoint){
        System.err.println(" check ++ " + joinPoint );
    }
}
