package model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	public Seat(Sector sector, int number) {
		super();
		this.sector = sector;
		this.number = number;
	}
	
	public Seat(){}
}
