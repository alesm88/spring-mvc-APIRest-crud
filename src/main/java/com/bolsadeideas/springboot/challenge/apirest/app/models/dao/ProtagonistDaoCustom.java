package com.bolsadeideas.springboot.challenge.apirest.app.models.dao;

import java.util.List;
import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Protagonist;

public interface ProtagonistDaoCustom {

	public List<Protagonist> findProtagonistsWithFilters(String name, Integer age, Double weight, String idFilm);
}
