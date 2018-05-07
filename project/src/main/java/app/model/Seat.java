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
	private Sector sector;
	
	@Column(name="snumber")
	private Long number;
	
	@OneToMany(mappedBy = "seat",cascade = CascadeType.REFRESH, fetch = FetchType.LAZY) 
	@JsonManagedReference
	private Set<QuickTicket> qtickets = new HashSet<QuickTicket>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Sector getSector() {
		return sector;
	}
	public void setSector(Sector sector) {
		this.sector = sector;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	
	
	
	public Seat(Long id, Sector sector, Long number, Set<QuickTicket> qtickets) {
		super();
		this.id = id;
		this.sector = sector;
		this.number = number;
		this.qtickets = qtickets;
	}
	public Seat(){}
}
