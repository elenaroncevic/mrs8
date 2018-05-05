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
@Table(name = "CinemaAdmin")
public class CinemaAdmin {
	
	@Id
	@GeneratedValue
	@Column(name="caid")
	private int id;
	
	@OneToMany(mappedBy = "cinemaadmin") //???
	private Set<Cinema> cinemas = new HashSet<Cinema>();
	
	@Column(name="caemail")
	private String email;
	
	@Column(name="capassword")
	private String password;
	
	public CinemaAdmin() {
		super();
	}

	

	public CinemaAdmin(int id, Set<Cinema> cinemas, String email, String password) {
		super();
		this.id = id;
		this.cinemas = cinemas;
		this.email = email;
		this.password = password;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Cinema> getCinemas() {
		return cinemas;
	}

	public void setCinemas(Set<Cinema> cinemas) {
		this.cinemas = cinemas;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}  
	
	
	
	
}
