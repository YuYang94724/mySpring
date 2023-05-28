package com.spring.spring6.aop.xmlAop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//切面類別
@Component //ioc容器
public class LogAspect {

    //設置切入點和通知類型
    //切入點表達式: execution(權限修飾 返回類型  增強方法所在類別的路徑 方法名稱(方法參數))
    //通知類型:
    // 前置 @Before(value = "切入點表達式 來配置 切入點")
    public void beforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("Logger-->前置通知, 方法名: " + methodName + ", 參數: " + Arrays.toString(args));

    }
    // 後置 @After()
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("Logger-->後置通知, 方法名: " + methodName + ", 參數: " + Arrays.toString(args));
    }
    // 返回 @AfterReturning
    public void afterReturningMethod(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        int i = 0;
        if (result instanceof Integer){
             i = (Integer) result + 10;
        }
        System.out.println("Logger-->返回通知, 方法名: " + methodName + ", 參數: " + Arrays.toString(args)
        + " 回傳的值 + 10 = " + i);
    }
    // 異常 @AfterThrowing 獲取目標方法異常訊息
    //目標方法出現異常, 這個通知執行
    public void afterThrowingMethod(JoinPoint joinPoint, Throwable ex){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("Logger-->異常通知, 方法名: " + methodName + ", 參數: " + Arrays.toString(args)
        + " 異常訊息 = " + ex);
    }
    // 環繞 @Around()
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint){
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint .getArgs();
        String argsString = Arrays.toString(args);
        Object result = null;
        try {
            System.out.println("環繞通知==目標方法之前執行");
            //調用目標方法
            result = proceedingJoinPoint.proceed();
            System.out.println("環繞通知==目標方法返回值之後執行");
        }catch (Throwable throwable){
            throwable.printStackTrace();
            System.out.println("環繞通知==目標方法出現異常執行");
        }finally {
            System.out.println("環繞通知==目標方法執行完畢最後執行");
        }
        return result;
    }
}
