package com.bolsadeideas.springboot.challenge.apirest.app.models.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;

@Document(collection = "genres")
public class Genre {
	
	@Id
	private String id;
	
	@NotBlank(message = "Image cannot be empty.")
    @Size(max = 100, message = "Image cannot be more than 100 characters.")
	private String image;
	
	@NotBlank(message = "Name cannot be empty.")
    @Size(max = 100, message = "Name cannot be more than 100 characters.")
	private String name;
	
	@DBRef(lazy = true)
	private Set<Film> films;
	
	public Genre() {
		films = new HashSet<>();
	}

	public Genre(String image, String name) {
		this.image = image;
		this.name = name;
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
	
	public Set<Film> getFilms() {
		return films;
	}

	public void setFilms(Set<Film> films) {
		this.films = films;
	}

	@Override
	public String toString() {
		return "Genre [id=" + id
				+ ", image=" + image
				+ ", name=" + name + "]";
	}
	
	

}
