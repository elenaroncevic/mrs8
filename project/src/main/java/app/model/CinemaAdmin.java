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
	
	
	
	public CinemaAdmin() {
		super();
	}

	

	public CinemaAdmin(int id, Set<Cinema> cinemas) {
		super();
		this.cinemas = cinemas;

	}
	public Set<Cinema> getCinemas() {
		return cinemas;
	}

	public void setCinemas(Set<Cinema> cinemas) {
		this.cinemas = cinemas;
	}

}
