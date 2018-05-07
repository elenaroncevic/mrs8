package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class SystemAdmin extends User{
	
	private boolean def;
	
	@Column(name="saemail")
	private String email;
	
	@Column(name="sapassword")
	private String password;

	public SystemAdmin() {
		super();
	}

	public SystemAdmin( boolean def, String email, String password) {
		super();
		this.def = def;
		this.email = email;
		this.password = password;
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
