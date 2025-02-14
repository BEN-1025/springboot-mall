package com.ben.springbootmall.service;

import com.ben.springbootmall.dto.UserLoginRequest;
import com.ben.springbootmall.dto.UserRegisterRequest;
import com.ben.springbootmall.model.User;


public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);

    User login (UserLoginRequest userLoginRequest);
}
