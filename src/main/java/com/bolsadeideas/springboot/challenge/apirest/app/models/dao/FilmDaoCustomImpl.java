package com.bolsadeideas.springboot.challenge.apirest.app.models.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Film;

@Repository
public class FilmDaoCustomImpl implements FilmDaoCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<Film> findFilmsWithFilters(String title, String genreId, String order) {
		
		Query query = new Query();

        if (title != null && !title.isEmpty()) {
            query.addCriteria(Criteria.where("title").regex(title, "i")); // Not case sensitive
        }

        if (genreId != null && !genreId.isEmpty()) {
            query.addCriteria(Criteria.where("genres._id").is(genreId)); // Search by gender reference ID
        }

        if (order != null && !order.isEmpty()) {
        	if (order.equalsIgnoreCase("ASC")) {
        		query.with(Sort.by(Sort.Direction.ASC, "created")); // Order by created attribute
        	}
        	if (order.equalsIgnoreCase("DESC")) {
        		query.with(Sort.by(Sort.Direction.DESC, "created"));
        	}
        }
		
        return mongoTemplate.find(query, Film.class);
	}

}
