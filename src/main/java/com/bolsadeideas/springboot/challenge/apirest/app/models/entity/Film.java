package com.bolsadeideas.springboot.challenge.apirest.app.models.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;

@Document(collection = "films")
public class Film {

	@Id
	private String id;
	
	@NotBlank(message = "Image cannot be empty.")
    @Size(max = 100, message = "Image cannot be more than 100 characters.")
	private String image;
	
	@NotBlank(message = "Title cannot be empty.")
    @Size(max = 100, message = "Title cannot be more than 100 characters.")
	private String title;
	
	@NotNull(message = "Created date cannot be null.")
    @PastOrPresent(message = "Created date cannot be future.")
	private Date created;
	
	@NotNull(message = "Score cannot be null.")
    @Min(value = 1, message = "Score cannot be less than 1.")
    @Max(value = 5, message = "Score cannot be more than 5.")
	private Integer score;
	
	@DBRef(lazy = true)
	private Set<Protagonist> protagonists;

	@DBRef(lazy = true)
	private Set<Genre> genres;
	
	public Film() {
		protagonists = new HashSet<>();
		genres = new HashSet<>();
	}

	public Film(String image, String title, Date created, Integer score) {
		this.image = image;
		this.title = title;
		this.created = created;
		this.score = score;
		protagonists = new HashSet<>();
		genres = new HashSet<>();
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	public Set<Protagonist> getProtagonists() {
		return protagonists;
	}

	public void setProtagonists(Set<Protagonist> protagonists) {
		this.protagonists = protagonists;
	}
	
	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(created, genres, id, image, protagonists, score, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return Objects.equals(created, other.created) && Objects.equals(genres, other.genres)
				&& Objects.equals(id, other.id) && Objects.equals(image, other.image)
				&& Objects.equals(protagonists, other.protagonists) && Objects.equals(score, other.score)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Film [id=" + id
				+ ", image=" + image
				+ ", title=" + title
				+ ", created=" + created
				+ ", score=" + score + "]";
	}
	
}
