package com.ben.springbootmall.rowmapper;

import com.ben.springbootmall.model.User;
import org.apache.catalina.Group;
import org.apache.catalina.Role;

import org.apache.catalina.UserDatabase;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setCreateDate(resultSet.getTimestamp("created_Date"));
        user.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return user;

    }
}
