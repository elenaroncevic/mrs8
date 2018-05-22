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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Sector {

	@Id
	@GeneratedValue
	@Column(name="sid") 
	private Long id;
	 
	@Column(name="snumber")
	private Long number;
	
	@OneToMany(mappedBy = "sector",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)//nisam sigurna da li i zasto je ovo sector
	@JsonManagedReference
	private Set<Seat> seats = new HashSet<Seat>();

	

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

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

	public Sector(Long id, Set<Seat> seats, Long number) {
		super();
		this.seats = seats;
		this.id = id;
		this.number = number;
		
	}

	public Sector() {
		super();
	}

	
	
}