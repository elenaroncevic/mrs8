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
	
	public User() {
		super();
	}
	
	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
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
	
}
