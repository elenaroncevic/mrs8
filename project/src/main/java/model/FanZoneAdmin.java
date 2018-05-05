package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FanZoneAdmin")
public class FanZoneAdmin{
	@Id
	@GeneratedValue
	@Column(name="fzaid")
	private int id;
	
	@Column(name="fzaemail")
	private String email;
	
	@Column(name="fzapassword")
	private String password;
	
	
	public FanZoneAdmin() {
		super();
	}
	
	
	
	public FanZoneAdmin(int id, String email, String password) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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
