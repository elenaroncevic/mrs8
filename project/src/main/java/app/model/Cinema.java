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
	private Long id;
	
	
	@OneToMany(mappedBy = "cinema",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH) 
	@JsonManagedReference
	private Set<Auditorium> auditoriums = new HashSet<Auditorium>();
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private CinemaAdmin admin;
	
	@OneToMany(mappedBy = "cinema",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonManagedReference
	private Set<PromoOfficial> promos = new HashSet<PromoOfficial>();
	
	@OneToMany(mappedBy ="cinema",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonManagedReference
	private Set<CinemaRate> rates = new HashSet<CinemaRate>();
	
	


	public CinemaAdmin getAdmin() {
		return admin;
	}




	public void setAdmin(CinemaAdmin admin) {
		this.admin = admin;
	}




	public Set<PromoOfficial> getPromos() {
		return promos;
	}




	public void setPromos(Set<PromoOfficial> promos) {
		this.promos = promos;
	}




	public Set<Auditorium> getAuditoriums() {
		return auditoriums;
	}


	public void setAuditoriums(Set<Auditorium> auditoriums) {
		this.auditoriums = auditoriums;
	}


	public Cinema() {
		super();
	}
	
	
	public Cinema(String name, String location, String description, double rating, Long id, Set<Auditorium> auditoriums,
			CinemaAdmin admin, Set<PromoOfficial> promos, Set<CinemaRate> rates) {
		super();
		this.name = name;
		this.location = location;
		this.description = description;
		this.rating = rating;
		this.id = id;
		this.auditoriums = auditoriums;
		this.admin = admin;
		this.promos = promos;
		this.rates = rates;
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

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
