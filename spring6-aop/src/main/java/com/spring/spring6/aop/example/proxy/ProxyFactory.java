package com.spring.spring6.aop.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

//動態代理
public class ProxyFactory {
    //目標對象
    public Object target;
    public ProxyFactory(Object target){
        this.target = target;
    }

    //返回代理對象
    public Object getProxy() {
        /**
         * Proxy.newProxyInstance();方法有三個參數
         * 1.
         **/
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        InvocationHandler invocationHandler = new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                //方法调用之前输出
                System.out.println("[动态代理][日志] "+method.getName()+"，参数："+ Arrays.toString(args));

                //调用目标的方法
                Object result = method.invoke(target, args);

                //方法调用之后输出
                System.out.println("[动态代理][日志] "+method.getName()+"，结果："+ result);
                return result;
            }
        };
        return Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}
