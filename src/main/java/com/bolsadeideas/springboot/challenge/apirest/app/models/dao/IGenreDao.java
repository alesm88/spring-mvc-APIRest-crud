package com.bolsadeideas.springboot.challenge.apirest.app.models.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Genre;

@Repository
public interface IGenreDao extends MongoRepository<Genre, String>/*, IProtagonistDaoCustom*/ {

	
}
