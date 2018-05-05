package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Cinema")
public class Cinema {
	@Column(name="cname")
	private String name;
	
	@Column(name="clocation")
	private String location;
	
	@Column(name="cdescription")
	private String description;
	
	@Column(name="crating")
	private double rating;
	
	@Id
	@GeneratedValue
	@Column(name="cid")
	private int id;
	
	@Column(name="cactors")
	private String actors;
	
	@OneToMany(mappedBy = "cinema") //nisam sigurna da li i zasto je ovo cinema
	private Set<Auditorium> auditoriums = new HashSet<Auditorium>();
	
	public Cinema(int id,String name, String location, String description, double rating,
			String actors, Set<Auditorium> auditoriums ) {
		super();
		this.name = name;
		this.location = location;
		this.description = description;
		this.rating = rating;
		this.actors = actors;
		this.id = id;
		this.auditoriums = auditoriums;
	}
	
	
	public Set<Auditorium> getAuditoriums() {
		return auditoriums;
	}


	public void setAuditoriums(Set<Auditorium> auditoriums) {
		this.auditoriums = auditoriums;
	}


	public String getActors() {
		return actors;
	}
	public void setActors(String actors) {
		this.actors = actors;
	}
	public Cinema() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
