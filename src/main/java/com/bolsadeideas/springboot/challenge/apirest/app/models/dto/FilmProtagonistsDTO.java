package com.bolsadeideas.springboot.challenge.apirest.app.models.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilmProtagonistsDTO {
	
	private String id;
	private String image;
	private String title;
	private Date created;
	private Integer score;
	private List<String> protagonists;
	
	public FilmProtagonistsDTO() {
		protagonists = new ArrayList<>();
	}
	
	public FilmProtagonistsDTO(String id, String image, String title, Date created, Integer score, List<String> protagonists) {
		this.id = id;
		this.image = image;
		this.title = title;
		this.created = created;
		this.score = score;
		this.protagonists = new ArrayList<>();
		this.protagonists = protagonists;
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

	public List<String> getProtagonists() {
		return protagonists;
	}

	public void setProtagonists(List<String> protagonists) {
		this.protagonists = protagonists;
	}

}
