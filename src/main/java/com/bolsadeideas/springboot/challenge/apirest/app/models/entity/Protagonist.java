package com.bolsadeideas.springboot.challenge.apirest.app.models.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;

@Document(collection = "protagonists")
public class Protagonist {
	
	@Id
	private String id;
	
	@NotBlank(message = "Image cannot be empty.")
    @Size(max = 100, message = "Image cannot be more than 100 characters.")
	private String image;
	
	@NotBlank(message = "Name cannot be empty.")
    @Size(max = 100, message = "Name cannot be more than 100 characters.")
	private String name;
	
	@NotNull(message = "Age cannot be null.")
	private Integer age;
	
	@NotNull(message = "Weight cannot be null.")
	private Double weight;
	
	@NotBlank(message = "History cannot be empty.")
    @Size(max = 100, message = "History cannot be more than 100 characters.")
	private String history;
	
	@DBRef(lazy = true)
	private Set<Film> films;
	
	public Protagonist() {
		films = new HashSet<>();
	}

	public Protagonist(String image, String name, Integer age, Double weight, String history) {
		this.image = image;
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.history = history;
		films = new HashSet<>();
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
	
	public Set<Film> getFilms() {
		return films;
	}

	public void setFilms(Set<Film> films) {
		this.films = films;
	}

	@Override
	public String toString() {
		return "Character [id=" + id
				+ ", image=" + image
				+ ", name=" + name
				+ ", age=" + age
				+ ", weight=" + weight
				+ ", history=" + history + "]";
	}

}
