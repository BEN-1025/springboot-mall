package com.ben.springbootmall.service.impl;

import com.ben.springbootmall.dao.UserDao;
import com.ben.springbootmall.dto.UserRegisterRequest;
import com.ben.springbootmall.model.User;
import com.ben.springbootmall.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
