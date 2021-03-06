package app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "CinemaRate")
public class CinemaRate {
	@Id
	@GeneratedValue
	@Column(name="crid")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private RegisteredUser voter;
	
	
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private Cinema cinema;
	
	@Column(name="crrating")
	private Double rating;


	public CinemaRate(Long id, RegisteredUser voter, Cinema cinema, Double rating) {
		super();
		this.id = id;
		this.voter = voter;
		this.cinema = cinema;
		this.rating = rating;
	}


	public CinemaRate() {
		super();
	}


	public Cinema getCinema() {
		return cinema;
	}


	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}


	public Double getRating() {
		return rating;
	}


	public void setRating(Double rating) {
		this.rating = rating;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public RegisteredUser getVoter() {
		return voter;
	}


	public void setVoter(RegisteredUser voter) {
		this.voter = voter;
	}
	
	
	
	
	
}
