package com.bolsadeideas.springboot.challenge.apirest.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.challenge.apirest.app.models.dao.IFilmDao;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dao.IProtagonistDao;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.ProtagonistFilmsDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Protagonist;

@Service
public class ProtagonistService {

	@Autowired
	IProtagonistDao protagonistDao;
	
	@Autowired
	IFilmDao filmDao;
	
	public List<Protagonist> findAllProtagonists() {
		return protagonistDao.findAll();
	}

	public Protagonist findProtagonist(String id) {
		return protagonistDao.findById(id).orElse(null);
	}

	public Protagonist createUpdateProtagonist(Protagonist protagonist) {
		return protagonistDao.save(protagonist);
	}

	public void deleteProtagonist(String id) {
		protagonistDao.deleteById(id);
	}
	
	public List<Protagonist> getProtagonistsWithFilters(String name, Integer age, Double weight, String filmId) {
		return protagonistDao.findProtagonistsWithFilters(name, age, weight, filmId);
	}
	
    public ProtagonistFilmsDTO getProtagonistWithFilms(String protagonistId) {
    	Protagonist protagonist = protagonistDao.findById(protagonistId).orElse(null);
    	List<String> filmTitles = protagonist.getFilms().stream()
    			.map(f -> filmDao.findById(f.getId()).orElseThrow().getTitle())
    			.toList();
    	
    	ProtagonistFilmsDTO dto = new ProtagonistFilmsDTO();
    	dto.setId(protagonist.getId());
    	dto.setImage(protagonist.getImage());
    	dto.setName(protagonist.getName());
    	dto.setAge(protagonist.getAge());
    	dto.setWeight(protagonist.getWeight());
    	dto.setHistory(protagonist.getHistory());
    	dto.setFilms(filmTitles);
    	
    	return dto;
    }

}
