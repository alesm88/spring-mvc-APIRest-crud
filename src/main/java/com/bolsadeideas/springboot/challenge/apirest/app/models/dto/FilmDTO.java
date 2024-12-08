package com.bolsadeideas.springboot.challenge.apirest.app.models.dto;

import java.util.Date;

public class FilmDTO {
	
	private String id;
	private String image;
	private String title;
	private Date created;
	
	public FilmDTO() {
		
	}
	
	public FilmDTO(String id, String image, String title, Date created) {
		this.id = id;
		this.image = image;
		this.title = title;
		this.created = created;
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
	
}
