package com.spring.dao.impl;

import com.spring.anno.MyBean;
import com.spring.dao.UserDao;

@MyBean
public class UserDaoImpl implements UserDao {
    @Override
    public void add() {
        System.out.println("dao........");
    }
}