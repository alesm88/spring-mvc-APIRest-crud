package com.bolsadeideas.springboot.challenge.apirest.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.FilmDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.FilmProtagonistsDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Film;
import com.bolsadeideas.springboot.challenge.apirest.app.models.service.FilmService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/films")
public class FilmController {

	@Autowired
	private FilmService filmService;
	
	@GetMapping({"/", ""})
	public ResponseEntity<?> getAllFilms(@RequestParam(required = false) String title,
											@RequestParam(required = false) String genreId,
											@RequestParam(required = false) String order) {
		List<FilmDTO> films = null;
		Map<String, Object> response = new HashMap<>();
		
		if (title == null && genreId == null && order == null) {
        	films = filmService.findAllFilms();
        } else {
        	try {
        		films = filmService.getFilmsWithFilters(title, genreId, order);
        	} catch (DataAccessException e) {
        		response.put("message", "Error to do query in DB");
    			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
    			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        	}
        	if (films.isEmpty() || films == null) {
        		response.put("message", "There is no film in DB with those search criteria.");
    			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        	}
        }
		return new ResponseEntity<List<FilmDTO>>(films, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showFilm(@PathVariable String id) {
		FilmProtagonistsDTO fpDTO = null;
		Map<String, Object> response = new HashMap<>();
		try {
			fpDTO = filmService.getFilmWithProtagonists(id);
		} catch (DataAccessException e) {
			response.put("message", "Error to do the query into the DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (fpDTO == null) {
			response.put("message", "The protagonist ID: ".concat(id.toString().concat(" does not exist in DB!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<FilmProtagonistsDTO>(fpDTO, HttpStatus.OK);
	}
    
    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody Film film, BindingResult result) { 
    	Film filmNew = null;
    	FilmProtagonistsDTO fpDTO = null;
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "The field '" + err.getField()  + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			filmNew = filmService.createUpdateFilm(film);
			fpDTO = filmService.getFilmWithProtagonists(filmNew.getId());
		} catch (DataAccessException e) {
			response.put("message", "Error to do the insert in DB or to bring the film created");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The film has been created successfully!");
		response.put("film", fpDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
    
    @PostMapping("/{filmId}/protagonists/{protagonistId}")
    public ResponseEntity<?> addProtagonistInFilm(@PathVariable String filmId, @PathVariable String protagonistId) { 
    	FilmProtagonistsDTO fpDTO = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			filmService.addProtagonistToFilm(protagonistId, filmId);
			fpDTO = filmService.getFilmWithProtagonists(filmId);
		} catch (DataAccessException e) {
			response.put("message", "Error adding the protagonist to the film in DB or bringing the film");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The protagonist has been added successfully into the film!");
		response.put("film", fpDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Film film, BindingResult result, @PathVariable String id) {
		
    	Film currentFilm = filmService.getFilm(id);
    	Film filmUpdated = null;
		
		FilmProtagonistsDTO fpDTO = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "The field '" + err.getField()  + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (currentFilm == null) {
			response.put("message", "Error: failed to edit, the film with ID: ".concat(id.concat(" does not exist in DB!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			currentFilm.setImage(film.getImage());
			currentFilm.setTitle(film.getTitle());
			currentFilm.setCreated(film.getCreated());
			currentFilm.setScore(film.getScore());
			
			filmUpdated = filmService.createUpdateFilm(currentFilm);
			fpDTO = filmService.getFilmWithProtagonists(filmUpdated.getId());
		} catch (DataAccessException e) {
			response.put("message", "Error to update the film in DB or to bring the film updated");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The film has been updated successfully!");
		response.put("film", fpDTO);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
		Map<String, Object> response = new HashMap<>();
		FilmProtagonistsDTO fpDTO = filmService.getFilmWithProtagonists(id);
		if (fpDTO == null) {
			response.put("message", "Error: it was impossible to delete, the film with ID: ".concat(id.concat(" does not exist in DB!")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			filmService.deleteFilm(id);
		} catch (DataAccessException e) {
			response.put("message", "Error deleting the film in DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "The film has been eliminated!");
		response.put("film", fpDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
    
    @DeleteMapping("/{filmId}/protagonists/{protagonistId}")
    public ResponseEntity<?> removeProtagonistInFilm(@PathVariable String filmId, @PathVariable String protagonistId) { 
    	FilmProtagonistsDTO fpDTO = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			filmService.removeProtagonistFromFilm(protagonistId, filmId);
			fpDTO = filmService.getFilmWithProtagonists(filmId);
		} catch (DataAccessException e) {
			response.put("message", "Error removing the protagonist from the film in DB or bringing the film");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The protagonist has been removed successfully from the film!");
		response.put("film", fpDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
    
}
