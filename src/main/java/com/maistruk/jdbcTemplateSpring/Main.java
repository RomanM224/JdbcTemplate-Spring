package com.maistruk.jdbcTemplateSpring;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.maistruk.jdbcTemplateSpring.config.AppConfig;
import com.maistruk.jdbcTemplateSpring.dao.JdbcUserDao;
import com.maistruk.jdbcTemplateSpring.dao.UserDao;
import com.maistruk.jdbcTemplateSpring.model.User;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Scanner scanner = new Scanner(System.in);
        int select;
        do {
            System.out.println("1. Create User");
            System.out.println("2. Update User");
            System.out.println("3. Delete User by id");
            System.out.println("4. Get User by id");
            System.out.print("Choice menu: ");
            select = scanner.nextInt();
            scanner.nextLine();
            selectOption(select, scanner, context);
        } while (select != 0);
    }

    private static void selectOption(int select, Scanner scanner, AnnotationConfigApplicationContext context) {
        if (select == 1) {
            createUser(scanner, context.getBean(UserDao.class));
        } else if (select == 2) {
            updateUser(scanner, context.getBean(UserDao.class));
        } else if (select == 3) {
            deleteUser(scanner, context.getBean(UserDao.class));
        } else if (select == 4) {
            getUser(scanner, context.getBean(JdbcUserDao.class));
        }
    }

    private static void createUser(Scanner scanner, UserDao userDao) {
        System.out.println("Insert user name: ");
        User user = new User();
        user.setName(scanner.nextLine());
        System.out.println("Insert date (2020-05-05): ");
        user.setDate(LocalDate.parse(scanner.nextLine()));
        System.out.println("Insert time (19:30): ");
        user.setTime(LocalTime.parse(scanner.nextLine()));
        System.out.println("Insert date and time (2020-05-05T19:30): ");
        user.setDateTime(LocalDateTime.parse(scanner.nextLine()));
        userDao.create(user);
    }

    private static void updateUser(Scanner scanner, UserDao userDao) {
        User user = new User();
        System.out.println("Insert user id: ");
        user.setId(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Insert user name: ");
        user.setName(scanner.nextLine());
        System.out.println("Insert date (2020-05-05): ");
        user.setDate(LocalDate.parse(scanner.nextLine()));
        System.out.println("Insert time (19:30): ");
        user.setTime(LocalTime.parse(scanner.nextLine()));
        System.out.println("Insert date and time (2020-05-05T19:30): ");
        user.setDateTime(LocalDateTime.parse(scanner.nextLine()));
        userDao.update(user);

    }

    private static void deleteUser(Scanner scanner, UserDao userDao) {
        System.out.println("Insert user id: ");
        userDao.delete(scanner.nextInt());
        scanner.nextLine();

    }

    private static void getUser(Scanner scanner, JdbcUserDao userDao) {
        System.out.println("Insert user id: ");
        System.out.println(userDao.getUser(scanner.nextInt()));
        scanner.nextLine();     
    }
}
