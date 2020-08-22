package com.maistruk.jdbcTemplateSpring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.maistruk.jdbcTemplateSpring.model.User;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int row) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setDate(LocalDate.parse(resultSet.getString("date")));
        user.setTime(LocalTime.parse(resultSet.getString("time")));
        String dateTime = resultSet.getString("timedate");
        dateTime = dateTime.replace(" ", "T");
        user.setDateTime(LocalDateTime.parse(dateTime));
        return user;
    }

}
