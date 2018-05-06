package model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Sector {

	@Id
	@GeneratedValue
	@Column(name="sid")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	@Column (name="sauditorium")
	private Auditorium auditorium;
	
	@OneToMany(mappedBy = "sector",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)//nisam sigurna da li i zasto je ovo sector
	private Set<Seat> seats = new HashSet<Seat>();


	public Set<Seat> getSeats() {
		return seats;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Auditorium getAuditorium() {
		return auditorium;
	}

	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}



	public Sector(Long id, Auditorium auditorium, Set<Seat> seats) {
		super();
		this.seats = seats;
		this.id = id;
		this.auditorium = auditorium;
		
	}

	public Sector() {
		super();
	}

	
	
}