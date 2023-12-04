package com.example.jpaspringdemo.aop;

import com.example.jpaspringdemo.dtos.ApiResponse;
import com.example.jpaspringdemo.dtos.DataDto;
import com.example.jpaspringdemo.dtos.DepartmentDto;
import com.example.jpaspringdemo.exceptions.NoSuchElementFoundException;
import com.example.jpaspringdemo.jpa.entities.Department;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Optional;

@Aspect
@Component
public class LoggingAspect {

  @Pointcut("within(com.example.jpaspringdemo.services.impl.DepartmentServiceImpl)")
  public void departmentServiceImplPointcut() {}

  @Pointcut("departmentServiceImplPointcut() && execution(public * *(..))")
  public void departmentServiceMethodPointcut() {}

  @Pointcut("departmentServiceImplPointcut() && execution(* addDepartment(..))")
  public void addDepartmentPointcut() {}

  @Pointcut("departmentServiceImplPointcut() && execution(* getDepartment(..))")
  public void getDepartmentPointcut() {}

  @Pointcut("departmentServiceImplPointcut() && execution(* updateDepartment(..))")
  public void updateDepartmentPointcut() {}

  @Pointcut("departmentServiceImplPointcut() && execution(* updateDepartment(..))")
  public void deleteDepartmentPointcut() {}

  @Around("departmentServiceMethodPointcut()")
  public Object departmentServiceAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature ms = (MethodSignature) joinPoint.getSignature();
    String cn = ms.getDeclaringType().getSimpleName();
    String mn = ms.getName();

    StopWatch sw = new StopWatch();
    sw.start();
    try {
      return joinPoint.proceed();
    } finally {
      sw.stop();
      System.out.printf("%s::%s executed in %-6.6f seconds.\n",
          cn, mn, sw.getTotalTimeSeconds());
    }
  }

  @AfterReturning(value = "departmentServiceMethodPointcut()", returning = "returnValue")
  public void departmentServiceAfterReturningAdvice(JoinPoint joinPoint, Object returnValue) {
    MethodSignature sig = (MethodSignature) joinPoint.getSignature();
    String cn = sig.getDeclaringType().getSimpleName();
    String mn = sig.getName();
    Optional<Department> dop = extractDepartment(returnValue);

    System.out.printf("%s::%s returned %s\n", cn, mn, dop.orElse(null));
  }

  @AfterThrowing(value = "departmentServiceMethodPointcut()", throwing = "t")
  public void departmentServiceAfterThrowingAdvice(JoinPoint joinPoint, Throwable t) {
    StringBuilder b = new StringBuilder();

    try {
      NoSuchElementFoundException e = (NoSuchElementFoundException) t;
      b.append("Department was not found at id: ")
          .append(joinPoint.getArgs()[0]);
    } catch (ClassCastException ignored) {
      b.append("Department data is invalid.");
    }
    System.out.println(b);
  }

  @Before("addDepartmentPointcut()")
  public void addDepartmentBeforeAdvice(JoinPoint joinPoint) {
    StringBuilder b = new StringBuilder("Requested to add new department: ");
    Optional<Department> dop = extractDepartmentFromJoinPoint(joinPoint);

    b.append(dop.orElse(null));
    System.out.println(b);
  }

  @Before("getDepartmentPointcut()")
  public void getDepartmentBeforeAdvice(JoinPoint joinPoint) {
    System.out.println("Requested to get department at id: " + joinPoint.getArgs()[0]);
  }

  @Before("updateDepartmentPointcut()")
  public void updateDepartmentBeforeAdvice(JoinPoint joinPoint) {
    System.out.println("Requested update on department at id: " + joinPoint.getArgs()[0]);
  }

  @Before("deleteDepartmentPointcut()")
  public void deleteDepartmentBeforeAdvice(JoinPoint joinPoint) {
    System.out.println("Requested to delete department at id: " + joinPoint.getArgs()[0]);
  }

  private static Optional<Department> extractDepartmentFromJoinPoint(JoinPoint joinPoint) {
    DataDto<DepartmentDto> dataDto = (DataDto<DepartmentDto>) joinPoint.getArgs()[0];
    return Optional.of(new Department(dataDto.getData()));
  }

  private static Optional<Department> extractDepartment(Object object) {
    try {
      ApiResponse res = (ApiResponse) object;
      Optional<Department> dop = Optional.of((Department) res.getResponse().get("Department"));
      return dop;
    } catch (ClassCastException e) {
      return Optional.empty();
    }
  }

}
