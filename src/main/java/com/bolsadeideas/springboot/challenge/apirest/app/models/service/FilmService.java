package com.bolsadeideas.springboot.challenge.apirest.app.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.challenge.apirest.app.models.dao.IFilmDao;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dao.IProtagonistDao;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.FilmDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.FilmProtagonistsDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Film;
import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Protagonist;

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
    	List<Film> films = filmDao.findFilmsWithFilters(title, genreId, order);
    	List<FilmDTO> filmsDTO = films.stream()
    			.map(f -> new FilmDTO(f.getId(), f.getImage(), f.getTitle(), f.getCreated()))
    			.collect(Collectors.toList());
    	return filmsDTO;
	}
    
    @Transactional // To avoid saving if something goes wrong in the process (ACID principles)
    public void addProtagonistToFilm(String protagonistId, String filmId) {
        
        Protagonist protagonist = protagonistDao.findById(protagonistId)
                .orElseThrow(() -> new RuntimeException("Protagonist not found with id: " + protagonistId));
        
        Film film = filmDao.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + filmId));

        if (film.getProtagonists() != null && film.getProtagonists().contains(protagonist)) {
            throw new RuntimeException("Protagonist is already associated with the film.");
        }
        
        if (protagonist.getFilms() != null && protagonist.getFilms().contains(film)) {
            throw new RuntimeException("Film is already associated with the Protagonist.");
        }

        film.getProtagonists().add(protagonist);

        protagonist.getFilms().add(film);

        filmDao.save(film);
        protagonistDao.save(protagonist);
    }
    
    @Transactional // To avoid removing if something goes wrong in the process (ACID principles)
    public void removeProtagonistFromFilm(String protagonistId, String filmId) {
        
        Protagonist protagonist = protagonistDao.findById(protagonistId)
                .orElseThrow(() -> new RuntimeException("Protagonist not found with id: " + protagonistId));
        
        Film film = filmDao.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + filmId));

        if (film.getProtagonists() == null || !film.getProtagonists().contains(protagonist)) {
            throw new RuntimeException("Protagonist is not associated with the film.");
        }
        
        if (protagonist.getFilms() == null || !protagonist.getFilms().contains(film)) {
            throw new RuntimeException("Film is not associated with the Protagonist.");
        }

        film.getProtagonists().remove(protagonist);

        protagonist.getFilms().remove(film);

        filmDao.save(film);
        protagonistDao.save(protagonist);
    }

}
