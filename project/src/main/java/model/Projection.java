package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Table(name="Projection")
public class Projection {
	
	@Id
	@GeneratedValue
	@Column(name="pid")
	private int id;
	
	@ManyToOne
	@JoinColumn (name="mid")
	@JsonBackReference
	private Movie movie;
	
	@Column(name="ptime")
	private Date date;
	
	private double price;
	
	@ManyToOne
	@JoinColumn (name="aid")
	@JsonBackReference
	@Column (name="pauditorium")
	private Auditorium auditorium;
	
	
	
	public Projection(int id, Movie movie, Date date, double price, Auditorium auditorium) {
		super();
		this.id = id;
		this.movie = movie;
		this.date = date;
		this.price = price;
		this.auditorium = auditorium;
	}
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Projection(){}
	
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Auditorium getAuditorium() {
		return auditorium;
	}
	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}
	
	
}
