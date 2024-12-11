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

import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.ProtagonistDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.ProtagonistFilmsDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Protagonist;

import com.bolsadeideas.springboot.challenge.apirest.app.models.service.ProtagonistService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/protagonists")
public class ProtagonistController {

	@Autowired
	private ProtagonistService protagonistService;
	
	@GetMapping({"/", ""})
	public ResponseEntity<?> getAllProtagonists(@RequestParam(required = false) String name,
											@RequestParam(required = false) Integer age,
											@RequestParam(required = false) Double weight,
											@RequestParam(required = false) String filmId) {
		List<ProtagonistDTO> protagonists = null;
		Map<String, Object> response = new HashMap<>();
		
		if (name == null && age == null && weight == null && filmId == null) {
        	protagonists = protagonistService.findAllProtagonists();
        } else {
        	try {
        		protagonists = protagonistService.getProtagonistsWithFilters(name, age, weight, filmId);
        	} catch (DataAccessException e) {
        		response.put("message", "Error to do query in DB");
    			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
    			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        	}
        	if (protagonists.isEmpty() || protagonists == null) {
        		response.put("message", "There is no protagonist in DB with those search criteria.");
    			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        	}
        }
		return new ResponseEntity<List<ProtagonistDTO>>(protagonists, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showProtagonist(@PathVariable String id) {
		ProtagonistFilmsDTO pfDTO = null;
		Map<String, Object> response = new HashMap<>();
		try {
			pfDTO = protagonistService.getProtagonistWithFilms(id);
		} catch (DataAccessException e) {
			response.put("message", "Error to do the query into the DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (pfDTO == null) {
			response.put("message", "The protagonist ID: ".concat(id.toString().concat(" does not exist in DB!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ProtagonistFilmsDTO>(pfDTO, HttpStatus.OK);
	}
	/*
    public ProtagonistFilmsDTO findProtagonist(@PathVariable String id) {
        return protagonistService.getProtagonistWithFilms(id);
    }*/
    
    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody Protagonist protagonist, BindingResult result) { 
		Protagonist protagonistNew = null;
		ProtagonistFilmsDTO pfDTO = null;
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
			protagonistNew = protagonistService.createUpdateProtagonist(protagonist);
			pfDTO = protagonistService.getProtagonistWithFilms(protagonistNew.getId());
		} catch (DataAccessException e) {
			response.put("message", "Error to do the insert in DB or to bring the protagonist created");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The protagonist has been created successfully!");
		response.put("protagonist", pfDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
    /*public ProtagonistFilmsDTO createProtagonist(@RequestBody Protagonist protagonist) {
        Protagonist p = protagonistService.createUpdateProtagonist(protagonist);
        return protagonistService.getProtagonistWithFilms(p.getId());
    }*/
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Protagonist protagonist, BindingResult result, @PathVariable String id) {
		
		Protagonist currentProtagonist = protagonistService.getProtagonist(id);
		Protagonist protagonistUpdated = null;
		
		ProtagonistFilmsDTO pfDTO = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "The field '" + err.getField()  + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (currentProtagonist == null) {
			response.put("message", "Error: failed to edit, the protagonist with ID: ".concat(id.concat(" does not exist in DB!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			currentProtagonist.setImage(protagonist.getImage());
			currentProtagonist.setName(protagonist.getName());
			currentProtagonist.setAge(protagonist.getAge());
			currentProtagonist.setWeight(protagonist.getWeight());
			currentProtagonist.setHistory(protagonist.getHistory());
			
			protagonistUpdated = protagonistService.createUpdateProtagonist(currentProtagonist);
			pfDTO = protagonistService.getProtagonistWithFilms(protagonistUpdated.getId());
		} catch (DataAccessException e) {
			response.put("message", "Error to update the protagonist in DB or to bring the protagonist updated");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The protagonist has been updated successfully!");
		response.put("protagonist", pfDTO);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
    /*public ProtagonistFilmsDTO updateProtagonist(@RequestBody Protagonist protagonist, @PathVariable String id) {
    	Protagonist p = protagonistService.createUpdateProtagonist(protagonist);
    	return protagonistService.getProtagonistWithFilms(p.getId());
    }*/
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
		Map<String, Object> response = new HashMap<>();
		ProtagonistFilmsDTO pfDTO = protagonistService.getProtagonistWithFilms(id);
		if (pfDTO == null) {
			response.put("message", "Error: it was impossible to delete, the protagonist with ID: ".concat(id.concat(" does not exist in DB!")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			protagonistService.deleteProtagonist(id);
		} catch (DataAccessException e) {
			response.put("message", "Error deleting the protagonist in DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "The protagonist has been eliminated!");
		response.put("protagonist", pfDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
    /*public void deleteProtagonist(@PathVariable String id) {
        protagonistService.deleteProtagonist(id);
    }*/
    
}
