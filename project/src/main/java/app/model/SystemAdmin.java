package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class SystemAdmin extends User{
	
	private Boolean def;
	

	public SystemAdmin() {
		super();
	}



	public SystemAdmin(Boolean def) {
		super();
		this.def = def;
	}



	public Boolean isDef() {
		return def;
	}



	public void setDef(Boolean def) {
		this.def = def;
	}


	
	
	
}
