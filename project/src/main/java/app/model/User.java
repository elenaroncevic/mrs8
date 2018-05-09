package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public  class User {

	@Id
	@Column(name= "email", nullable=false, unique=true)
	private String email;
	private String password;
	private String activated;
	
	public User() {
		super();
	}
	
	

	public User(String email, String password, String activated) {
		super();
		this.email = email;
		this.password = password;
		this.activated = activated;
	}



	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}



	public String getActivated() {
		return activated;
	}



	public void setActivated(String activated) {
		this.activated = activated;
	}
	
	
	
}
