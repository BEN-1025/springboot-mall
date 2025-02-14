package com.ben.springbootmall.dao;

import com.ben.springbootmall.dto.UserRegisterRequest;
import com.ben.springbootmall.model.User;


public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
