package model;

import java.util.ArrayList;
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
@Table(name = "Auditorium")
public class Auditorium {
	@ManyToOne
	@JoinColumn (name="cid")
	@JsonBackReference
	@Column (name="acinema")
	private Cinema cinema;
	
	@OneToMany(mappedBy = "auditorium") //nisam sigurna da li i zasto je ovo cinema
	private Set<Sector> sectors = new HashSet<Sector>();
	
	@OneToMany(mappedBy = "auditorium") //nisam sigurna da li i zasto je ovo cinema
	private Set<Projection> projections = new HashSet<Projection>();

	@Id
	@GeneratedValue
	@Column(name="aid")
	private int id;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public Set<Projection> getProjections() {
		return projections;
	}

	public void setProjections(Set<Projection> projections) {
		this.projections = projections;
	}

	public Auditorium( int id, Cinema cinema, Set<Sector> sectors, Set<Projection> projections) {
		super();
		this.cinema = cinema;
		this.sectors = sectors;
		this.id = id;
		this.projections = projections;
	}

	public Auditorium() {
		super();
	}

	
}