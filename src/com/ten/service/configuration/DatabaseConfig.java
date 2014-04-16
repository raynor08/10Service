package com.ten.service.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
import com.ten.service.configuration.DatabaseConfig.DatabaseProperties;
import com.ten.service.dao.UserDAOMongo;

@Configuration
@EnableConfigurationProperties(DatabaseProperties.class)
public class DatabaseConfig {
    @Autowired
    private DatabaseProperties config;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate =
                new MongoTemplate(new MongoClient(config.getDbHost()), config.getDbName());
        return mongoTemplate;
    }

    @Bean
    public UserDAOMongo userDAO(MongoTemplate mongoDb) {
        return new UserDAOMongo(mongoDb);
    }

    @ConfigurationProperties(prefix = "database.mongo")
    public static class DatabaseProperties {
        private String dbName;
        private String dbHost;
        private String dbUserCollection;

        public String getDbUserCollection() {
            return dbUserCollection;
        }

        public void setDbUserCollection(String dbUserCollection) {
            this.dbUserCollection = dbUserCollection;
        }

        public String getDbHost() {
            return dbHost;
        }

        public void setDbHost(String dbHost) {
            this.dbHost = dbHost;
        }

        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }
    }
}
