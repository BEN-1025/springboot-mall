package com.ben.springbootmall.service.impl;

import com.ben.springbootmall.dao.UserDao;
import com.ben.springbootmall.dto.UserLoginRequest;
import com.ben.springbootmall.dto.UserRegisterRequest;
import com.ben.springbootmall.model.User;
import com.ben.springbootmall.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        User user=userDao.getUserByEmail(userRegisterRequest.getEmail());
        if(user!=null){
            log.warn("該email:{}已被註冊",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //使用MD5 生成密碼的hash值
        String hashedPassword= DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user=userDao.getUserByEmail(userLoginRequest.getEmail());
       //檢查user是否存在
        if(user == null){
            log.warn("該email:{}尚未註冊",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //使用MD5生成密碼hash值
        String hashedPassword= DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
        //比較密碼
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }else{
            log.warn("email密碼:{}不正確...", userLoginRequest.getPassword());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
