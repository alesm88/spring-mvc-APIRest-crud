package com.bolsadeideas.springboot.challenge.apirest.app.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.challenge.apirest.app.models.dao.IFilmDao;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dao.IProtagonistDao;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.FilmDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.FilmProtagonistsDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Film;

@Service
public class FilmService {

	@Autowired
	IFilmDao filmDao;
	
	@Autowired
	IProtagonistDao protagonistDao;
	
	public List<FilmDTO> findAllFilms() {
		 List<Film> films = filmDao.findAll();
		 
		 List<FilmDTO> filmsDTO = films.stream()
		            .map(f -> new FilmDTO(f.getId(), 
		            						f.getImage(), 
		            						f.getTitle(),
		            						f.getCreated()))
		            .collect(Collectors.toList());
		 
		 return filmsDTO;
	}
	
	public Film getFilm(String filmId) {
		return filmDao.findById(filmId).orElse(null);
	}

	public FilmProtagonistsDTO getFilmWithProtagonists(String filmId) {
		Film film = filmDao.findById(filmId).orElse(null);
    	
    	List<String> protagonistsName = film.getProtagonists().stream()
    			.map(p -> protagonistDao.findById(p.getId()).orElseThrow().getName())
    			.toList();
    	
    	FilmProtagonistsDTO dto = new FilmProtagonistsDTO();
    	dto.setId(film.getId());
    	dto.setImage(film.getImage());
    	dto.setTitle(film.getTitle());
    	dto.setCreated(film.getCreated());
    	dto.setScore(film.getScore());
    	dto.setProtagonists(protagonistsName);
    	
    	return dto;
    }

	public Film createUpdateFilm(Film film) {
		return filmDao.save(film);
	}

	public void deleteFilm(String id) {
		filmDao.deleteById(id);
	}
    
    public List<FilmDTO> getFilmsWithFilters(String title, String genreId, String order) {
		return null; //filmDao.findFilmsWithFilters(title, genreId, order);
	}

}
