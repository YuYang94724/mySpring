package com.spring.spring6.aop.example.impl;

import com.spring.spring6.aop.example.Calculator;

public class CalculatorImpl implements Calculator {
    @Override
    public int add(int i, int j) {
        int result = i + j;

        System.out.println("add result = " + result);

        return result;
    }

    @Override
    public int sub(int i, int j) {
        int result = i - j;

        System.out.println("sub result = " + result);

        return result;
    }

    @Override
    public int mul(int i, int j) {
        int result = i * j;

        System.out.println("mul result = " + result);

        return result;
    }

    @Override
    public int div(int i, int j) {
        int result = i / j;

        System.out.println("div result = " + result);

        return result;
    }
}
