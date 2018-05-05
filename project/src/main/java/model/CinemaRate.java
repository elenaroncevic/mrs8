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
@Table(name = "CinemaRate")
public class CinemaRate {
	@Id
	@GeneratedValue
	@Column(name="crid")
	private int id;
	
	//kad se odkomentarise treba get/set za ovo polje i konstruktori
	/*@ManyToOne
	@JoinColumn (name="ruid")
	@JsonBackReference
	@Column (name="cvoter")
	private RegisteredUser voter;
	
	*/
	
	@ManyToOne
	@JoinColumn (name="cid")
	@JsonBackReference
	@Column (name="crcinema")
	private Cinema cinema;
	
	@Column(name="crrating")
	private int rating;


	public Cinema getCinema() {
		return cinema;
	}


	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
}
