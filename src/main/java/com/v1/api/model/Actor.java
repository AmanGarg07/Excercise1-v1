package com.v1.api.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="actors")
public class Actor {

	@Id
	private String id;
	private String name;
	private List<String> movies;
	
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getMovies() {
		return movies;
	}
	public void setMovies(List<String> movies) {
		this.movies = movies;
	}
	public Actor(String id, String name,List<String> movies) {
		super();
		this.id = id;
		this.name=name;
		this.movies = movies;
	}   
}
