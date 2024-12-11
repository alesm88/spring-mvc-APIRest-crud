package com.bolsadeideas.springboot.challenge.apirest.app.models.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Protagonist;

@Repository
public class ProtagonistDaoCustomImpl implements ProtagonistDaoCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<Protagonist> findProtagonistsWithFilters(String name, Integer age, Double weight, String idFilm) {
		
		Query query = new Query();

        if (name != null && !name.isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(name, "i")); // Search not case sensitive
        }

        if (age != null) {
            query.addCriteria(Criteria.where("age").is(age));
        }

        if (weight != null) {
            query.addCriteria(Criteria.where("weight").is(weight));
        }

        if (idFilm != null && !idFilm.isEmpty()) {
            query.addCriteria(Criteria.where("films._id").is(idFilm)); // Comparison with the reference of Film
        }

        return mongoTemplate.find(query, Protagonist.class);
	}

}
