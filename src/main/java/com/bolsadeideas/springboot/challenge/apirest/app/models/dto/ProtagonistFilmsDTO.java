package com.bolsadeideas.springboot.challenge.apirest.app.models.dto;

import java.util.ArrayList;
import java.util.List;

public class ProtagonistFilmsDTO {
	
	private String id;
	private String image;
	private String name;
	private Integer age;
	private Double weight;
	private String history;
	private List<String> films;
	
	public ProtagonistFilmsDTO() {
		films = new ArrayList<>();
	}
	
	public ProtagonistFilmsDTO(String id, String image, String name, Integer age, Double weight, String history, List<String> films) {
		this.id = id;
		this.image = image;
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.history = history;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public List<String> getFilms() {
		return films;
	}

	public void setFilms(List<String> films) {
		this.films = films;
	}

}
