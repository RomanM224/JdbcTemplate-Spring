package com.maistruk.jdbcTemplateSpring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.maistruk.jdbcTemplateSpring.TestConfig;
import com.maistruk.jdbcTemplateSpring.dao.JdbcUserDao;
import com.maistruk.jdbcTemplateSpring.model.User;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class JdbcUserDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcUserDao userDao;

    @Test
    public void createNewUser_whenCreated_thenGetGeneratedId() {
        User expectedUser = new User();
        expectedUser.setId(4);
        expectedUser.setName("Roma");
        expectedUser.setDate(LocalDate.parse("2020-05-05"));
        expectedUser.setTime(LocalTime.parse("19:30"));
        expectedUser.setDateTime(LocalDateTime.parse("2020-05-05T19:30"));
        User actualUser = new User();
        actualUser.setName("Roma");
        actualUser.setDate(LocalDate.parse("2020-05-05"));
        actualUser.setTime(LocalTime.parse("19:30"));
        actualUser.setDateTime(LocalDateTime.parse("2020-05-05T19:30"));
        userDao.create(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void givenExistingUser_whenUpdate_thenUpdated() {
        User expectedUser = new User();
        expectedUser.setId(3);
        expectedUser.setName("Ida");
        expectedUser.setDate(LocalDate.parse("2020-07-07"));
        expectedUser.setTime(LocalTime.parse("12:30"));
        expectedUser.setDateTime(LocalDateTime.parse("2020-05-05T19:30"));
        userDao.update(expectedUser);

        Integer rowsInDatabaseExpected = 1;
        Integer rowsInDatabaseActual = countRowsInTableWhere(jdbcTemplate, "users",
                "id = 3 and name = 'Ida' and date = '2020-07-07' and time = '12:30' and timedate = '2020-05-05 19:30'");
        assertEquals(rowsInDatabaseExpected, rowsInDatabaseActual);
    }

    @Test
    public void givenUserId_whenGetUser_thenGetUser() {
        User expectedUser = new User();
        expectedUser.setId(2);
        expectedUser.setName("Vika");
        expectedUser.setDate(LocalDate.parse("2020-06-06"));
        expectedUser.setTime(LocalTime.parse("04:30"));
        expectedUser.setDateTime(LocalDateTime.parse("2020-05-05T19:30"));
        User actualUser = userDao.getUser(2);
        assertEquals(expectedUser, actualUser);
    }
    
    @Test
    public void givenUserId_whenDelete_thenDeleter() {
        Integer rowsBeforeDelete = countRowsInTable(jdbcTemplate, "users");
        
        userDao.delete(2);
        Integer rowsAfterDelete = countRowsInTable(jdbcTemplate, "users");
        assertEquals(rowsBeforeDelete - 1, rowsAfterDelete);
    }

}
