package model;

import java.util.HashSet;
import java.util.Set;

import javax.imageio.IIOImage;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "Movie")
public class Movie {
	@Id
	@GeneratedValue
	@Column(name="mid")
	private int id;
	
	private String title;

	@Column(name="mrating")
	private double rating;
	private IIOImage poster;
	private String director;
	private String actors;
	private int duration;
	
	@Column (name="mdescription")
	private String description;
	
	@OneToMany(mappedBy = "movie")
	private Set<MovieRate> rates = new HashSet<MovieRate>();
	
	@OneToMany(mappedBy = "movie")
	private Set<Projection> projections = new HashSet<Projection>();
	
	
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
	public IIOImage getPoster() {
		return poster;
	}
	public void setPoster(IIOImage poster) {
		this.poster = poster;
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
	public Movie(int id, String title, double rating, IIOImage poster, String director, String actors, int duration,
			String description, Set<MovieRate> rates, Set<Projection> projections) {
		super();
		this.id = id;
		this.title = title;
		this.rating = rating;
		this.poster = poster;
		this.director = director;
		this.actors = actors;
		this.duration = duration;
		this.description = description;
		this.rates = rates;
		this.projections = projections;
	}
	
	
}
