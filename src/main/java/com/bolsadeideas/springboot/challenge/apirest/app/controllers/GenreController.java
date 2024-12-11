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

import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.GenreFilmsDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Genre;
import com.bolsadeideas.springboot.challenge.apirest.app.models.service.GenreService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/genres")
public class GenreController {

	@Autowired
	private GenreService genreService;
	
	@GetMapping({"/", ""})
	public List<GenreFilmsDTO> getAllGenres() {
		return genreService.findAllGenres();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showGenre(@PathVariable String id) {
		GenreFilmsDTO gfDTO = null;
		Map<String, Object> response = new HashMap<>();
		try {
			gfDTO = genreService.getGenreWithFilms(id);
		} catch (DataAccessException e) {
			response.put("message", "Error to do the query into the DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (gfDTO == null) {
			response.put("message", "The genre ID: ".concat(id.concat(" does not exist in DB!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<GenreFilmsDTO>(gfDTO, HttpStatus.OK);
	}
    
    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody Genre genre, BindingResult result) { 
		Genre genreNew = null;
		GenreFilmsDTO gfDTO = null;
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
			genreNew = genreService.createUpdateGenre(genre);
			gfDTO = genreService.getGenreWithFilms(genreNew.getId());
		} catch (DataAccessException e) {
			response.put("message", "Error to do the insert in DB or to bring the genre created");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The genre has been created successfully!");
		response.put("genre", gfDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Genre genre, BindingResult result, @PathVariable String id) {
		
		Genre currentGenre = genreService.getGenre(id);
		Genre genreUpdated = null;
		
		GenreFilmsDTO gfDTO = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "The field '" + err.getField()  + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (currentGenre == null) {
			response.put("message", "Error: failed to edit, the genre with ID: ".concat(id.concat(" does not exist in DB!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			currentGenre.setImage(genre.getImage());
			currentGenre.setName(genre.getName());
			
			genreUpdated = genreService.createUpdateGenre(currentGenre);
			gfDTO = genreService.getGenreWithFilms(genreUpdated.getId());
		} catch (DataAccessException e) {
			response.put("message", "Error to update the genre in DB or to bring the protagonist updated");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The genre has been updated successfully!");
		response.put("genre", gfDTO);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
		Map<String, Object> response = new HashMap<>();
		GenreFilmsDTO gfDTO = genreService.getGenreWithFilms(id);
		if (gfDTO == null) {
			response.put("message", "Error: it was impossible to delete, the genre with ID: ".concat(id.concat(" does not exist in DB!")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			genreService.deleteGenre(id);
		} catch (DataAccessException e) {
			response.put("message", "Error deleting the genre in DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "The genre has been eliminated!");
		response.put("genre", gfDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
    
}
