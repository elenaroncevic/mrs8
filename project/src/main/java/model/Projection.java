package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
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
	
	@OneToMany(mappedBy = "projection") 
	private Set<QuickTicket> qtickets = new HashSet<QuickTicket>();
	
	public Projection(){}
	
	
	
	
	public Projection(int id, Movie movie, Date date, double price, Auditorium auditorium, Set<QuickTicket> qtickets) {
		super();
		this.id = id;
		this.movie = movie;
		this.date = date;
		this.price = price;
		this.auditorium = auditorium;
		this.qtickets = qtickets;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	
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




	public Set<QuickTicket> getQtickets() {
		return qtickets;
	}




	public void setQtickets(Set<QuickTicket> qtickets) {
		this.qtickets = qtickets;
	}
	
	
	
}
