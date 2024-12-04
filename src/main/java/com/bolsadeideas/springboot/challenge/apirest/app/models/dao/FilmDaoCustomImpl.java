package com.bolsadeideas.springboot.challenge.apirest.app.models.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Film;

public class FilmDaoCustomImpl implements IFilmDaoCustom {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<Film> findFilmsByFilters(String title, String genreId, String order) {
		
		Criteria criteria = new Criteria();
		
		if (title != null && !title.isEmpty()) {
            criteria.and("title").regex(title, "i");
        }
        if (genreId != null) {
            criteria.and("genre._id").in(genreId);
        }
        
        Query query = new Query(criteria);
        
        if (order != null) {
        	if (order.equalsIgnoreCase("ASC")) {
        		query.with(Sort.by(Sort.Direction.ASC, "created"));
        	}
        	if (order.equalsIgnoreCase("DESC")) {
        		query.with(Sort.by(Sort.Direction.DESC, "created"));
        	}
        }
		
        return mongoTemplate.find(query, Film.class);
	}

}
