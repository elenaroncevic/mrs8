package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SystemAdmin")
public class SystemAdmin{
	@Id
	@GeneratedValue
	@Column(name="said")
	private int id;
	
	private boolean def;
	
	@Column(name="saemail")
	private String email;
	
	@Column(name="sapassword")
	private String password;

	public SystemAdmin() {
		super();
	}
	


	


	public SystemAdmin(int id, boolean def, String email, String password) {
		super();
		this.id = id;
		this.def = def;
		this.email = email;
		this.password = password;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public boolean isDef() {
		return def;
	}



	public void setDef(boolean def) {
		this.def = def;
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
