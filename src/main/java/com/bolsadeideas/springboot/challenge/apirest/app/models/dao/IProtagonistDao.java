package com.bolsadeideas.springboot.challenge.apirest.app.models.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Protagonist;

@Repository
public interface IProtagonistDao extends MongoRepository<Protagonist, String>, IProtagonistDaoCustom {

	/*
	List<Protagonist> findByName(String name);
	
    List<Protagonist> findByAge(int age);
    
    List<Protagonist> findByFilmsId(String filmId);
    */
}
