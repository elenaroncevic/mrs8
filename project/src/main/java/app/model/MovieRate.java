package app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "MovieRate")
public class MovieRate {
	@Id
	@GeneratedValue
	@Column(name="mrid")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private RegisteredUser voter;
		
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private Movie movie;
	
	@Column(name="mrrating")
	private Double rating;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public RegisteredUser getVoter() {
		return voter;
	}

	public void setVoter(RegisteredUser voter) {
		this.voter = voter;
	}

	public MovieRate(Long id, RegisteredUser voter, Movie movie, Double rating) {
		super();
		this.id = id;
		this.voter = voter;
		this.movie = movie;
		this.rating = rating;
	}

	public MovieRate() {
		super();
	}

}
