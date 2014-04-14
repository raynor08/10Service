package com.ten.service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
import com.ten.service.dao.UserDAOMongo;

@Configuration
public class DatabaseConfig {
    private static final String DB_NAME = "tenuserdb";

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(), DB_NAME);
        return mongoTemplate;

    }

    @Bean
    public UserDAOMongo userDAO(MongoTemplate mongoDb) {
        return new UserDAOMongo(mongoDb);
    }
}
