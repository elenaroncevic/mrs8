package app.dto;

import app.model.Movie;

public class MovieDTO {
	private String title;
	private double rating;
	private String director;
	private String actors;
	private int duration;
	private String description;
	
	public MovieDTO(Movie movie){
		this.title = movie.getTitle();
		this.rating = movie.getRating();
		this.director = movie.getDirector();
		this.actors = movie.getActors();
		this.duration = movie.getDuration();
		this.description = movie.getDescription();	
	}
	
	public MovieDTO(String title, double rating, String director, String actors, int duration, String description) {
		super();
		this.title = title;
		this.rating = rating;
		this.director = director;
		this.actors = actors;
		this.duration = duration;
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getActors() {
		return actors;
	}
	public void setActors(String actors) {
		this.actors = actors;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
