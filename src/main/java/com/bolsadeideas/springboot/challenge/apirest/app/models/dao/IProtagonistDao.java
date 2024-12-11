package com.bolsadeideas.springboot.challenge.apirest.app.models.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Protagonist;

public interface IProtagonistDao extends MongoRepository<Protagonist, String>, ProtagonistDaoCustom {

	/*
	List<Protagonist> findByName(String name);
	
    List<Protagonist> findByAge(int age);
    
    List<Protagonist> findByFilmsId(String filmId);
    */
}
