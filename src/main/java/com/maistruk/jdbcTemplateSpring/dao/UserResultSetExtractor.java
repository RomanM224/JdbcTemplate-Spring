package com.maistruk.jdbcTemplateSpring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.maistruk.jdbcTemplateSpring.model.User;

@Component
public class UserResultSetExtractor implements ResultSetExtractor<User> {

    @Override
    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
        User user = new User();
        while(rs.next()) {
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setDate(LocalDate.parse(rs.getString("date")));
            user.setTime(LocalTime.parse(rs.getString("time")));
            String dateTime = rs.getString("timedate");
            dateTime = dateTime.replace(" ", "T");
            user.setDateTime(LocalDateTime.parse(dateTime));
        }
        return user;
    }

}
