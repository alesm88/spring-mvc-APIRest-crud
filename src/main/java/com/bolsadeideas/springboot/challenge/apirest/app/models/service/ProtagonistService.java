package com.bolsadeideas.springboot.challenge.apirest.app.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.challenge.apirest.app.models.dao.IFilmDao;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dao.IProtagonistDao;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.ProtagonistDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.dto.ProtagonistFilmsDTO;
import com.bolsadeideas.springboot.challenge.apirest.app.models.entity.Protagonist;

@Service
public class ProtagonistService {

	@Autowired
	IProtagonistDao protagonistDao;
	
	@Autowired
	IFilmDao filmDao;
	
	public List<ProtagonistDTO> findAllProtagonists() {
		 List<Protagonist> protagonists = protagonistDao.findAll();
		 
		 List<ProtagonistDTO> protagonistsDTO = protagonists.stream()
		            .map(p -> new ProtagonistDTO(p.getId(), 
		            						p.getImage(), 
		            						p.getName()))
		            .collect(Collectors.toList());
		 
		 return protagonistsDTO;
	}
	
	public Protagonist getProtagonist(String protagonistId) {
		return protagonistDao.findById(protagonistId).orElse(null);
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

	public Protagonist createUpdateProtagonist(Protagonist protagonist) {
		return protagonistDao.save(protagonist);
	}

	public void deleteProtagonist(String id) {
		protagonistDao.deleteById(id);
	}
    
    public List<ProtagonistDTO> getProtagonistsWithFilters(String name, Integer age, Double weight, String filmId) {
		return null; //protagonistDao.findProtagonistsWithFilters(name, age, weight, filmId);
	}

}
