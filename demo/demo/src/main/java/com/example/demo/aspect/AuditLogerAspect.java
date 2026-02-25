package com.example.demo.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Log4j2(topic = "AUDITLOG")
@Aspect
@Service
public class AuditLogerAspect {
    @Around("execution (public * com.example.demo.controller..*(..))")
    public Object logAudit(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String className = signature.getDeclaringTypeName();  // Pełna nazwa klasy, np. "com.example.service.UserService"
        String simpleClassName = signature.getDeclaringType().getSimpleName();  // Krótka nazwa, np. "UserService"
        String methodName = signature.getName();  // Nazwa metody, np. "getUser"

        log.info("in: | {} | {} | {}" , simpleClassName, methodName, Arrays.toString(proceedingJoinPoint.getArgs()));
        Object result = proceedingJoinPoint.proceed();
        log.info("out: | {} | {} | {} | {}", simpleClassName, methodName, Arrays.toString(proceedingJoinPoint.getArgs()), result);

        return result;
    }

}
