package com.maistruk.jdbcTemplateSpring.dao;

import com.maistruk.jdbcTemplateSpring.model.User;

public interface UserDao {

    void create(User user);

    void update(User user);

    void delete(Integer id);

    User getUser(Integer id);

}
