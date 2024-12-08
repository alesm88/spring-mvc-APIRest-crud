package com.bolsadeideas.springboot.challenge.apirest.app.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.challenge.apirest.app.models.dao.IFilmDao;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dao.IGenreDao;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.GenreFilmsDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Film;
import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Genre;

@Service
public class GenreService {

	@Autowired
	IGenreDao genreDao;
	
	@Autowired
	IFilmDao filmDao;
	
	public List<GenreFilmsDTO> findAllGenres() {
		 List<Genre> genres = genreDao.findAll();
		 
		 List<GenreFilmsDTO> genresDTO = genres.stream()
		            .map(this::convertToDTO)
		            .collect(Collectors.toList());
		 
		 return genresDTO;
	}
	
	public Genre getGenre(String genreId) {
		return genreDao.findById(genreId).orElse(null);
	}

	public GenreFilmsDTO getGenreWithFilms(String genreId) {
    	Genre genre = genreDao.findById(genreId).orElse(null);
    	
    	List<String> filmTitles = genre.getFilms().stream()
    			.map(f -> filmDao.findById(f.getId()).orElseThrow().getTitle())
    			.toList();
    	
    	GenreFilmsDTO dto = new GenreFilmsDTO();
    	dto.setId(genre.getId());
    	dto.setImage(genre.getImage());
    	dto.setName(genre.getName());
    	dto.setFilms(filmTitles);
    	
    	return dto;
    }

	public Genre createUpdateGenre(Genre genre) {
		return genreDao.save(genre);
	}

	public void deleteGenre(String id) {
		genreDao.deleteById(id);
	}
	
	public GenreFilmsDTO convertToDTO(Genre genre) {
		GenreFilmsDTO gfDTO = new GenreFilmsDTO();
		gfDTO.setId(genre.getId());
		gfDTO.setName(genre.getName());
		gfDTO.setImage(genre.getImage());
		
		if (genre.getFilms() != null) {
			List<String> films = genre.getFilms().stream()
									.map(Film::getId)
									.collect(Collectors.toList());
			gfDTO.setFilms(films);
		} else {
			gfDTO.setFilms(List.of());
		}
		
        return gfDTO;
    }

}
