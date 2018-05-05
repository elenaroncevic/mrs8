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
@Table(name="Seat")
public class Seat {
	
	@Id
	@GeneratedValue
	@Column(name="seid")
	private int id;
	
	@ManyToOne
	@JoinColumn (name="sid")
	@JsonBackReference
	private Sector sector;
	
	@Column(name="snumber")
	private int number;
	
	@OneToMany(mappedBy = "seat") 
	private Set<QuickTicket> qtickets = new HashSet<QuickTicket>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Sector getSector() {
		return sector;
	}
	public void setSector(Sector sector) {
		this.sector = sector;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	
	
	public Seat(int id, Sector sector, int number, Set<QuickTicket> qtickets) {
		super();
		this.id = id;
		this.sector = sector;
		this.number = number;
		this.qtickets = qtickets;
	}
	public Seat(){}
}
