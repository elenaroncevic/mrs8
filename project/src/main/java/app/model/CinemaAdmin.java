package app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class CinemaAdmin extends User{
	
	@OneToMany(mappedBy = "admin",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH) 
	@JsonManagedReference
	private Set<Cinema> cinemas = new HashSet<Cinema>();
	
	private Boolean first_time;

	
	
	public Boolean getFirst_time() {
		return first_time;
	}




	public void setFirst_time(Boolean first_time) {
		this.first_time = first_time;
	}




	public CinemaAdmin() {
		super();
	}

	
	
	
	public CinemaAdmin(Set<Cinema> cinemas) {
		super();
		this.cinemas = cinemas;
	}
	
	public CinemaAdmin(Boolean first_time,Set<Cinema> cinemas) {
		super();
		this.first_time = first_time;
		this.cinemas = cinemas;
	}




	public Set<Cinema> getCinemas() {
		return cinemas;
	}

	public void setCinemas(Set<Cinema> cinemas) {
		this.cinemas = cinemas;
	}


	
}
