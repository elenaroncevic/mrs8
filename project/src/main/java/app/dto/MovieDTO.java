package app.dto;

import app.model.Movie;

public class MovieDTO {
	private String title;
	private Double rating;
	private String director;
	private String actors;
	private Integer duration;
	private String description;
	private Long id;
	private String genre;
	private String image;
	
	public MovieDTO(Movie movie){
		this.title = movie.getTitle();
		this.rating = movie.getRating();
		this.director = movie.getDirector();
		this.actors = movie.getActors();
		this.duration = movie.getDuration();
		this.description = movie.getDescription();	
		this.id = movie.getId();
		this.genre=movie.getGenre();
		this.image=movie.getImage();
	}
	
	public MovieDTO(String title, Double rating, String director, String actors, Integer duration, String description, 
			Long id, String genre, String image) {
		super();
		this.title = title;
		this.rating = rating;
		this.director = director;
		this.actors = actors;
		this.duration = duration;
		this.description = description;
		this.id=id;
		this.genre = genre;
		this.image = image;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
