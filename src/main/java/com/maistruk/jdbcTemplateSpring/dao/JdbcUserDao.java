package com.maistruk.jdbcTemplateSpring.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.maistruk.jdbcTemplateSpring.model.User;

@Repository
public class JdbcUserDao implements UserDao {

    private static final String ADD_USER = "INSERT INTO users (id, name, date, time, timedate) VALUES (DEFAULT, ?, ?, ?, ?)";
    private static final String ADD_USER_NAMED_PARAMETER = "INSERT INTO users (id, name, date, time, timedate) VALUES (DEFAULT, :name, :date, :time, :timedate)";
    private static final String UPDATE_USER = "UPDATE users SET name = ?, date = ?, time = ?, timedate = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String GET_USER = "SELECT * FROM users WHERE id = ?;";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate template;
    private UserRowMapper userRowMapper;
    private UserResultSetExtractor userResultSetExtractor;

    public JdbcUserDao(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper,
            UserResultSetExtractor userResultSetExtractor, NamedParameterJdbcTemplate template) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
        this.userResultSetExtractor = userResultSetExtractor;
        this.template = template;
    }

    public void create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_USER, new String[] { "id" });
            statement.setString(1, user.getName());
            statement.setObject(2, user.getDate());
            statement.setObject(3, user.getTime());
            statement.setObject(4, user.getDateTime());
            return statement;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        System.out.println(user.getId());
    }

    // NamedParameterJdbcTemplate Example
//    public void create(User user) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("name", user.getName());
//        map.put("date", user.getDate());
//        map.put("time", user.getTime());
//        map.put("timedate", user.getDateTime());
//        template.execute(ADD_USER_NAMED_PARAMETER, map, new PreparedStatementCallback() {
//            @Override
//            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//                return ps.executeUpdate();
//            }
//        });
//    }

    public void update(User user) {
        jdbcTemplate.update(UPDATE_USER, user.getName(), user.getDate(), user.getTime(), user.getDateTime(),
                user.getId());
    }

    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_USER, id);

    }

    // RowMapper Example
//    public User getUser(Integer id) {
//        List<User> users = jdbcTemplate.query(GET_USER, userRowMapper, id);
//        return users.get(0);
//    }

    // ResultSetExtractor example
    public User getUser(Integer id) {
        User user = jdbcTemplate.query(GET_USER, userResultSetExtractor, id);
        return user;
    }

}
