package model;


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
@Table(name="Sector")
public class Sector {

	@Id
	@GeneratedValue
	@Column(name="sid")
	private int id;
	
	@ManyToOne
	@JoinColumn (name="aid")
	@JsonBackReference
	@Column (name="sauditorium")
	private Auditorium auditorium;
	
	@OneToMany(mappedBy = "sector") //nisam sigurna da li i zasto je ovo sector
	private Set<Seat> seats = new HashSet<Seat>();


	public Set<Seat> getSeats() {
		return seats;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Auditorium getAuditorium() {
		return auditorium;
	}

	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}



	public Sector(int id, Auditorium auditorium, Set<Seat> seats) {
		super();
		this.seats = seats;
		this.id = id;
		this.auditorium = auditorium;
		
	}

	public Sector() {
		super();
	}

	
	
}