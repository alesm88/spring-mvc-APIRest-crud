package com.bolsadeideas.springboot.challenge.apirest.app.models.dto;

import java.util.ArrayList;
import java.util.List;

public class GenreFilmsDTO {
	
	private String id;
	private String image;
	private String name;
	private List<String> films;
	
	public GenreFilmsDTO() {
		films = new ArrayList<>();
	}
	
	public GenreFilmsDTO(String id, String image, String name, List<String> films) {
		this.id = id;
		this.image = image;
		this.name = name;
		this.films = new ArrayList<>();
		this.films = films;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getFilms() {
		return films;
	}

	public void setFilms(List<String> films) {
		this.films = films;
	}

}
