package com.spring.service.impl;

import com.spring.anno.Di;
import com.spring.anno.MyBean;
import com.spring.dao.UserDao;
import com.spring.service.UserService;
@MyBean
public class UserServiceImpl implements UserService {
    @Di
    private UserDao userDao;

    @Override
    public void add() {
        System.out.println("service......");
    }
}
