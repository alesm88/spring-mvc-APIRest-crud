package com.bolsadeideas.springboot.challenge.apirest.app.models.dao;

import java.util.List;

import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Film;

public interface IFilmDaoCustom {

	public List<Film> findFilmsWithFilters(String title, String genreId, String order);
}
