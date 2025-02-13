package com.ben.springbootmall.dao.impl;

import com.ben.springbootmall.dao.UserDao;
import com.ben.springbootmall.dto.UserRegisterRequest;
import com.ben.springbootmall.model.User;
import com.ben.springbootmall.rowmapper.UserRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql="insert into user(email,password,created_date,last_modified_date) values(:email,:password,:CreateDate,:lastModifiedDate)";
        Map<String,Object> map=new HashMap<>();
        map.put("email",userRegisterRequest.getEmail());
        map.put("password",userRegisterRequest.getPassword());

        Date now=new Date();
        map.put("CreateDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder=new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int userId=keyHolder.getKey().intValue();

        return userId;
    }

    @Override
    public User getUserById(Integer userId) {
        String sql="select user_id,email,password,created_date,last_modified_date from user where user_id=:userId";
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);

        List<User> userList=namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if (userList.size()>0){
            return userList.get(0);
        }else{
            return null;
        }
    }
}
