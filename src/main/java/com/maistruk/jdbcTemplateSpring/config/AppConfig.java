package com.maistruk.jdbcTemplateSpring.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("com.maistruk.jdbcTemplateSpring")
@PropertySource("classpath:config.properties")
public class AppConfig {
  
    @Autowired
    Environment environment;

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty("url"));
        driverManagerDataSource.setUsername(environment.getProperty("user"));
        driverManagerDataSource.setPassword(environment.getProperty("password"));
        driverManagerDataSource.setDriverClassName(environment.getProperty("driver"));
        return driverManagerDataSource;
    }
    
    @Bean
    JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
    
    @Bean
    NamedParameterJdbcTemplate NamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
