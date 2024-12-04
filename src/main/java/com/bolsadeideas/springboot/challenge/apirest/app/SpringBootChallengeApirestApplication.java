package com.bolsadeideas.springboot.challenge.apirest.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class SpringBootChallengeApirestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootChallengeApirestApplication.class, args);
	}

}
