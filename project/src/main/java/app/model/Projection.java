package app.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="Projection")
public class Projection {
	
	@Id
	@GeneratedValue
	@Column(name="pid")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private Movie movie;
	
	@Column(name="pdate")
	private String date;
	
	@Column(name="ptime")
	private String time;
	
	private Double price;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private Auditorium auditorium;
	
	@OneToMany(mappedBy = "projection", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH) 
	@JsonManagedReference
	private Set<Ticket> tickets = new HashSet<Ticket>();
	
	public Projection(){super();}
	
	

	public Projection(Long id, Movie movie, String date, String time, Double price, Auditorium auditorium,
			Set<Ticket> tickets) {
		super();
		this.id = id;
		this.movie = movie;
		this.date = date;
		this.time = time;
		this.price = price;
		this.auditorium = auditorium;
		this.tickets = tickets;
	}



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

	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}



	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Auditorium getAuditorium() {
		return auditorium;
	}
	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}








	public Set<Ticket> getTickets() {
		return tickets;
	}








	public void setReservations(Set<Ticket> tickets) {
		this.tickets = tickets;
	}




	
	
	
	
}
