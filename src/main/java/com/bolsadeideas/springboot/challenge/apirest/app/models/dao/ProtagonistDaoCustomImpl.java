package com.bolsadeideas.springboot.challenge.apirest.app.models.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Protagonist;

@Repository
public class ProtagonistDaoCustomImpl implements IProtagonistDaoCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<Protagonist> findProtagonistsWithFilters(String name, Integer age, Double weight, String idFilm) {
		
		Criteria criteria = new Criteria();
		
		if (name != null && !name.isEmpty()) {
            criteria.and("name").regex(name, "i");
        }
        if (age != null) {
            criteria.and("age").is(age);
        }
        if (weight != null) {
            criteria.and("weight").is(weight);
        }
        if (idFilm != null) {
            criteria.and("films._id").in(idFilm);
        }
		
        Query query = new Query(criteria);
		
        return mongoTemplate.find(query, Protagonist.class);
	}

}
