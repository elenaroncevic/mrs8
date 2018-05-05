package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class Sector {
	private ArrayList<Seat> seats;

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
	private Set<Seat> sectors = new HashSet<Seat>();

	public ArrayList<Seat> getSeats() {
		return seats;
	}

	public void setSeats(ArrayList<Seat> seats) {
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

	public Set<Seat> getSectors() {
		return sectors;
	}

	public void setSectors(Set<Seat> sectors) {
		this.sectors = sectors;
	}

	public Sector(ArrayList<Seat> seats, int id, Auditorium auditorium, Set<Seat> sectors) {
		super();
		this.seats = seats;
		this.id = id;
		this.auditorium = auditorium;
		this.sectors = sectors;
	}

	public Sector() {
		super();
	}

	
	
}