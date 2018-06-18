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
@Table(name = "audRow")
public class Row {
	@Id
	@GeneratedValue
	@Column(name="rid")
	private Long id;
	
	private int active;
	
	@Column(name="rnumber")
	private Long number;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private Auditorium auditorium;
	
	@OneToMany(mappedBy = "row",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)//nisam sigurna da li i zasto je ovo sector
	@JsonManagedReference
	private Set<Seat> seats = new HashSet<Seat>();

	

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

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

	public Auditorium getAuditorium() {
		return auditorium;
	}

	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}



	public Row(Long id, Auditorium auditorium, Set<Seat> seats, Long number) {
		super();
		this.seats = seats;
		this.id = id;
		this.auditorium = auditorium;
		this.number = number;
		
	}

	public Row() {
		super();
	}

	
	
}

