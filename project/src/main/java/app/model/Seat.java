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
@Table(name="Seat")
public class Seat {
	
	@Id
	@GeneratedValue
	@Column(name="seid")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private Auditorium auditorium;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private Row row;
	 
	@Column(name="snumber")
	private String number;
	

	
	@OneToMany(mappedBy = "seat",cascade = CascadeType.REFRESH, fetch = FetchType.LAZY) 
	@JsonManagedReference
	private Set<Ticket> tickets = new HashSet<Ticket>();
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private Sector sector=null;
	
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
	public Sector getSector() {
		return sector;
	}
	public void setSector(Sector sector) {
		this.sector = sector;
	}
	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}
	

	public Seat(){}
	public Seat(Long id, Auditorium auditorium, Row row,String number, Set<Ticket> tickets, Sector sector) {
		super();
		this.id = id;
		this.auditorium = auditorium;
		this.number = number;
		this.tickets = tickets;
		this.row = row;
		this.sector = sector;
	}
	
	
	
}
