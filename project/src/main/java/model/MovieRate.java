package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "MovieRate")
public class MovieRate {
	@Id
	@GeneratedValue
	@Column(name="mrid")
	private int id;
	//kad se otkomentarise treba dodati set/get i konstruktore
	/*@ManyToOne
	@JoinColumn (name="ruid")
	@JsonBackReference
	@Column (name="mrvoter")
	private RegisteredUser voter;
	
	*/
	
	@ManyToOne
	@JoinColumn (name="mid")
	@JsonBackReference
	@Column (name="mrmovie")
	private Cinema movie;
	
	@Column(name="mrrating")
	private int rating;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cinema getMovie() {
		return movie;
	}

	public void setMovie(Cinema movie) {
		this.movie = movie;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	
	
	
	
}
