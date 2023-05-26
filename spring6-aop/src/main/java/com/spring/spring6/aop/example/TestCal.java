package com.spring.spring6.aop.example;

import com.spring.spring6.aop.example.impl.CalculatorImpl;
import com.spring.spring6.aop.example.impl.CalculatorStaticProxy;
import com.spring.spring6.aop.example.proxy.ProxyFactory;

public class TestCal {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(new CalculatorImpl());
        Calculator proxy =  (Calculator)proxyFactory.getProxy();
        proxy.add(1, 2);
        System.out.println("==================");
        CalculatorStaticProxy c = new CalculatorStaticProxy(new CalculatorImpl());
        c.add(3, 4);
    }
}
