package com.bolsadeideas.springboot.challenge.apirest.app.models.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;

@Document(collection = "users")
public class User {
	
	@Id
	private ObjectId id;
	
	@NotBlank(message = "Mail cannot be empty.")
    @Size(max = 100, message = "Mail cannot be more than 100 characters.")
	@Email(message = "Mail format incorrect")
	private String email;
	
	@NotBlank(message = "Password cannot be empty.")
    @Size(max = 100, message = "Password cannot be more than 100 characters.")
	private String password;
	
	public User() {
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id
				+ ", email=" + email
				+ ", password=" + password + "]";
	}
	
	

}
