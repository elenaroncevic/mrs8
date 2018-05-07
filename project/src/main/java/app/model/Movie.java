package app.model;

import java.util.HashSet;
import java.util.Set;

import javax.imageio.IIOImage;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "Movie")
public class Movie {
	@Id
	@GeneratedValue
	@Column(name="mid")
	private Long id;
	
	private String title;

	@Column(name="mrating")
	private double rating;
	private String director;
	private String actors;
	private int duration;
	
	@Column (name="mdescription")
	private String description;
	
	@OneToMany(mappedBy = "movie",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonManagedReference
	private Set<MovieRate> rates = new HashSet<MovieRate>();
	
	@OneToMany(mappedBy = "movie")
	@JsonManagedReference
	private Set<Projection> projections = new HashSet<Projection>();
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<MovieRate> getRates() {
		return rates;
	}
	public void setRates(Set<MovieRate> rates) {
		this.rates = rates;
	}
	public Set<Projection> getProjections() {
		return projections;
	}
	public void setProjections(Set<Projection> projections) {
		this.projections = projections;
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
	
	
	public Movie(){}
	public Movie(Long id, String title, double rating, String director, String actors, int duration,
			String description, Set<MovieRate> rates, Set<Projection> projections) {
		super();
		this.id = id;
		this.title = title;
		this.rating = rating;
		this.director = director;
		this.actors = actors;
		this.duration = duration;
		this.description = description;
		this.rates = rates;
		this.projections = projections;
	}
	
	
}
