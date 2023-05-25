package com.spring;

import com.spring.bean.AnnotationApplicationContext;
import com.spring.bean.ApplicationContext;
import com.spring.service.UserService;

public class TestUser {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationApplicationContext("com.spring");
        UserService userService =(UserService) context.getBean(UserService.class);
        System.out.println(userService);
        userService.add();
    }
}
