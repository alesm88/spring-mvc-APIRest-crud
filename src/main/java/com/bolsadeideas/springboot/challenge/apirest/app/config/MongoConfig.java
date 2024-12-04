package com.bolsadeideas.springboot.challenge.apirest.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "com.bolsadeideas.springboot.challenge.apirest.app.models.dao")
public class MongoConfig {

	@Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "disney");
    }
}
