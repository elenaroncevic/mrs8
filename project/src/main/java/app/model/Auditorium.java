package app.model;


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
@Table(name = "Auditorium")
public class Auditorium {
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference	
	private Cinema cinema;
	 
	@OneToMany(mappedBy = "auditorium",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH) 
	@JsonManagedReference
	private Set<Seat> seats = new HashSet<Seat>();
	
	@OneToMany(mappedBy = "auditorium",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH) 
	@JsonManagedReference
	private Set<Row> rows = new HashSet<Row>();
	
	@OneToMany(mappedBy = "auditorium",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH) 
	@JsonManagedReference
	private Set<Projection> projections = new HashSet<Projection>();
	
	@OneToMany(mappedBy = "auditorium",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH) 
	@JsonManagedReference
	private Set<Sector> sectors = new HashSet<Sector>();


	@Id
	@GeneratedValue
	@Column(name="aid")
	private Long id;

	@Column(name="a_number")
	private Integer number;
	
	

	public Auditorium(Cinema cinema, Set<Seat> seats, Set<Row> rows, Set<Projection> projections, Set<Sector> sectors,
			Long id, Integer number) {
		super();
		this.cinema = cinema;
		this.seats = seats;
		this.rows = rows;
		this.projections = projections;
		this.sectors = sectors;
		this.id = id;
		this.number = number;
	}

	public Set<Seat> getSeats() {
		return seats;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

	public Set<Row> getRows() {
		return rows;
	}

	public void setRows(Set<Row> rows) {
		this.rows = rows;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public Set<Sector> getSectors() {
		return sectors;
	}

	public void setSectors(Set<Sector> sectors) {
		this.sectors = sectors;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Set<Projection> getProjections() {
		return projections;
	}

	public void setProjections(Set<Projection> projections) {
		this.projections = projections;
	}

	public Auditorium( Long id,Integer number, Cinema cinema, Set<Sector> sectors,Set<Row> rows, Set<Projection> projections) {
		super();
		this.cinema = cinema;
		this.sectors = sectors;
		this.id = id;
		this.projections = projections;
		this.rows = rows;
		this.number=number;
	}

	public Auditorium() {
		super();
	}

	
}