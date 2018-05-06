package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CinemaAdmin")
public class CinemaAdmin extends User{
	
	@OneToMany(mappedBy = "admin",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH) 
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
