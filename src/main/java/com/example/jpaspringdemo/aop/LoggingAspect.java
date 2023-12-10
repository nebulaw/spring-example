package com.example.jpaspringdemo.aop;

import com.example.jpaspringdemo.dtos.ApiResponse;
import com.example.jpaspringdemo.exceptions.NoSuchElementFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

    private static Object extractFromApiResponse(Object object) {
        try {
            ApiResponse res = (ApiResponse) object;

            Object retObj = res.getResponse().get("Department");
            if (retObj == null) {
                retObj = res.getResponse().get("Employee");
            }

            return retObj != null ? retObj : "list of objects";
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Pointcut("within(com.example.jpaspringdemo.services.impl.DepartmentServiceImpl)")
    public void departmentServiceImplPointcut() {
    }

    @Pointcut("within(com.example.jpaspringdemo.services.impl.EmployeeServiceImpl)")
    public void employeeServiceImplPointcut() {
    }

    @Pointcut("(departmentServiceImplPointcut() || employeeServiceImplPointcut()) && execution(* *(..))")
    public void serviceMethodPointcut() {
    }

    @Around("serviceMethodPointcut()")
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
            System.out.printf("%s::%s executed in %-6.6f seconds.\n", cn, mn, sw.getTotalTimeSeconds());
        }
    }

    @AfterReturning(value = "serviceMethodPointcut()", returning = "returnValue")
    public void departmentServiceAfterReturningAdvice(JoinPoint joinPoint, Object returnValue) {
        MethodSignature sig = (MethodSignature) joinPoint.getSignature();
        String cn = sig.getDeclaringType().getSimpleName();
        String mn = sig.getName();

        System.out.printf("%s::%s returned %s\n", cn, mn, extractFromApiResponse(returnValue));
    }

    @Pointcut("departmentServiceImplPointcut() && execution(* *(*))")
    public void departmentServiceMethodPointcut() {
    }

    @AfterThrowing(value = "departmentServiceMethodPointcut()", throwing = "t")
    public void departmentServiceAfterThrowingAdvice(JoinPoint joinPoint, Throwable t) {
        StringBuilder b = new StringBuilder();

        try {
            NoSuchElementFoundException e = (NoSuchElementFoundException) t;
            b.append("Department was not found at id: ").append(joinPoint.getArgs()[0]);
        } catch (ClassCastException ignored) {
            b.append("Department data is invalid.");
        }
        System.out.println(b);
    }


    @Before("addDepartmentPointcut()")
    public void addDepartmentBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("Requested to add new department: " + joinPoint.getArgs()[0]);
    }

    @Pointcut("departmentServiceImplPointcut() && execution(* addDepartment(..))")
    public void addDepartmentPointcut() {
    }


    @Before("getDepartmentPointcut()")
    public void getDepartmentBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("Requested to get department at id: " + joinPoint.getArgs()[0]);
    }

    @Pointcut("departmentServiceImplPointcut() && execution(* getDepartment(..))")
    public void getDepartmentPointcut() {
    }


    @Before("updateDepartmentPointcut()")
    public void updateDepartmentBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("Requested update on department at id: " + joinPoint.getArgs()[0]);
    }

    @Pointcut("departmentServiceImplPointcut() && execution(* updateDepartment(..))")
    public void updateDepartmentPointcut() {
    }


    @Before("deleteDepartmentPointcut()")
    public void deleteDepartmentBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("Requested to delete department at id: " + joinPoint.getArgs()[0]);
    }

    @Pointcut("departmentServiceImplPointcut() && execution(* updateDepartment(..))")
    public void deleteDepartmentPointcut() {
    }


    @Pointcut("employeeServiceImplPointcut() && execution(* *(*))")
    public void employeeServiceMethodPointcut() {
    }

    @AfterThrowing(value = "employeeServiceMethodPointcut()", throwing = "t")
    public void employeeServiceMethodAfterThrowingAdvice(JoinPoint joinPoint, Throwable t) {
        StringBuilder b = new StringBuilder();

        try {
            NoSuchElementFoundException e = (NoSuchElementFoundException) t;
            b.append("Employee was not found at id: ").append(joinPoint.getArgs()[0]);
        } catch (ClassCastException ignored) {
            b.append("Employee data is invalid.");
        }
        System.out.println(b);
    }


    @Before("addEmployeePointcut()")
    public void addEmployeeBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("Requested to add employee at department " + joinPoint.getArgs()[0]);
    }

    @Pointcut("employeeServiceImplPointcut() && execution(public * addEmployee(..))")
    public void addEmployeePointcut() {
    }


    @Before("getEmployeePointcut()")
    public void getEmployeeBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("Requested to get employee at id: " + joinPoint.getArgs()[0]);
    }

    @Pointcut("employeeServiceImplPointcut() && execution(public * getEmployee(..))")
    public void getEmployeePointcut() {
    }


    @Before("updateEmployeePointcut()")
    public void updateEmployeeBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("Requested to update employee at id: " + joinPoint.getArgs()[0]);
    }

    @Pointcut("employeeServiceImplPointcut() && execution(public * updateEmployee(..))")
    public void updateEmployeePointcut() {
    }


    @Before("deleteEmployeePointcut()")
    public void deleteEmployeeBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("Requested to delete employee at id: " + joinPoint.getArgs()[0]);
    }

    @Pointcut("employeeServiceImplPointcut() && execution(public * deleteEmployee(..))")
    public void deleteEmployeePointcut() {
    }

}
