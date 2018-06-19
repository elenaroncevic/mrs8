package app.dto;

import app.model.Cinema;
import app.model.Visitation;

public class VisitationDTO {

	private Long id;
	private String movie;
	private String cinema;
	private double cinemaRating;
	private double movieRating;
	
	public VisitationDTO(Visitation visit) {
		this.id=visit.getId();
		this.movie=visit.getTicket().getProjection().getMovie().getTitle();
		Cinema cin = visit.getTicket().getProjection().getAuditorium().getCinema();
		this.cinema= cin.getName();
		this.cinemaRating = cin.getRating();
		this.movieRating = visit.getTicket().getProjection().getMovie().getRating();
	}
	
	public VisitationDTO() {
		
	}

	public VisitationDTO(Long id, String movie, String cinema, double cinema_rating, double movie_rating) {
		super();
		this.id = id;
		this.movie = movie;
		this.cinema = cinema;
		this.cinemaRating = cinema_rating;
		this.movieRating = movie_rating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public String getCinema() {
		return cinema;
	}

	public void setCinema(String cinema) {
		this.cinema = cinema;
	}

	public double getCinemaRating() {
		return cinemaRating;
	}

	public void setCinemaRating(double cinema_rating) {
		this.cinemaRating = cinema_rating;
	}

	public double getMovieRating() {
		return movieRating;
	}

	public void setMovieRating(double movie_rating) {
		this.movieRating = movie_rating;
	}
	
	
}
