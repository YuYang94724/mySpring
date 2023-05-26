package com.spring.spring6.aop.annoAop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//切面類別
@Component //ioc容器
@Aspect //切面類
public class LogAspect {

    //設置切入點和通知類型
    //切入點表達式: execution(權限修飾 返回類型  增強方法所在類別的路徑 方法名稱(方法參數))
    //通知類型:
    // 前置 @Before(value = "切入點表達式 來配置 切入點")
//    @Before(value = "execution(* com.spring.spring6.aop.annoAop.impl.*.*(..))")
    @Before(value = "execution(public int com.spring.spring6.aop.annoAop.impl.CalculatorImpl.*(..))")
    public void beforeMethod(){
        System.out.println("Logger-->前置通知");
    }
    // 返回 @AfterReturning
    // 異常 @AfterThrowing
    // 後置 @After()
    // 環繞 @Around()

}
