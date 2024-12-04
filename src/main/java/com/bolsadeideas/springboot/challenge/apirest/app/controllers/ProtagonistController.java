package com.bolsadeideas.springboot.challenge.apirest.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.ProtagonistFilmsDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Protagonist;

import com.bolsadeideas.springboot.challenge.apirest.app.models.service.ProtagonistService;

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
		List<Protagonist> protagonists = null;
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
        		response.put("message", "The protagonist: ".concat(name.concat(" do not exist in DB!")));
    			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        	}
        }
		return new ResponseEntity<List<Protagonist>>(protagonists, HttpStatus.OK);
	}
	/*public List<Protagonist> getAllProtagonists(@RequestParam(required = false) String name,
											@RequestParam(required = false) Integer age,
											@RequestParam(required = false) Double weight,
											@RequestParam(required = false) String filmId) {
		if (name == null && age == null && weight == null && filmId == null) {
			return protagonistService.findAllProtagonists();
		} else {
			return protagonistService.getProtagonistsWithFilters(name, age, weight, filmId);
		}

	}*/
	
    @GetMapping("/{id}")
    public Protagonist findProtagonist(@PathVariable String id) {
        return protagonistService.findProtagonist(id);
    }
    
    @PostMapping("/")
    public ProtagonistFilmsDTO createProtagonist(@RequestBody Protagonist protagonist) {
        Protagonist p = protagonistService.createUpdateProtagonist(protagonist);
        return protagonistService.getProtagonistWithFilms(p.getId());
    }
    
    @PutMapping("/{id}")
    public ProtagonistFilmsDTO updateProtagonist(@RequestBody Protagonist protagonist, @PathVariable String id) {
    	Protagonist p = protagonistService.createUpdateProtagonist(protagonist);
    	return protagonistService.getProtagonistWithFilms(p.getId());
    }
    
    @DeleteMapping("/{id}")
    public void deleteProtagonist(@PathVariable String id) {
        protagonistService.deleteProtagonist(id);
    }
    
}
