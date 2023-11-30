package com.example.jpaspringdemo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggerAspect {

  @Pointcut("@annotation(Loggable)")
  public void loggableMethod() {

  }

  @Around("loggableMethod()")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    String className = methodSignature.getDeclaringType().getSimpleName();
    String methodName = methodSignature.getName();

    StopWatch stopWatch = new StopWatch();

    stopWatch.start();
    try {
      return joinPoint.proceed();
    } finally {
      stopWatch.stop();
      System.out.println(className + "." + methodName + ", Time: " + stopWatch.getTotalTimeSeconds());
    }
  }

  @Before("loggableMethod()")
  public void beforeMethod(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    String argsString = Arrays.stream(args)
        .map(Objects::toString)
        .collect(Collectors.joining(","));
    System.out.println("Method Arguments: " + argsString);
  }


}
