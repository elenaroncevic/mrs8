package app.dto;

import javax.persistence.Column;

import app.model.Cinema;

public class CinemaDTO {
	private Long id;
	private String name;
	private String location;
	private String description;
	private Double rating;
	
	public CinemaDTO() {
		
	}

	public CinemaDTO(Long id, String name, String location, String description, Double rating) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.description = description;
		this.rating = rating;
	}
	
	public CinemaDTO(Cinema cin) {
		this.id=cin.getId();
		this.name=cin.getName();
		this.location=cin.getLocation();
		this.description=cin.getDescription();
		this.rating=cin.getRating();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	

}
