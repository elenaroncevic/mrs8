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
	private Set<Projection> projections = new HashSet<Projection>();

	@Id
	@GeneratedValue
	@Column(name="aid")
	private Long id;
	
	@Column(name="a_number")
	private Integer number;
	
	@Column(name="r_number")
	private Integer rNumber;

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
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


	public Auditorium() {
		super();
	}

	public Set<Seat> getSeats() {
		return seats;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getRowNumber() {
		return rNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rNumber = rowNumber;
	}

	public Auditorium(Cinema cinema, Set<Seat> seats, Set<Projection> projections, Long id, Integer number,
			Integer rowNumber) {
		super();
		this.cinema = cinema;
		this.seats = seats;
		this.projections = projections;
		this.id = id;
		this.number = number;
		this.rNumber = rowNumber;
	}

	
	
}