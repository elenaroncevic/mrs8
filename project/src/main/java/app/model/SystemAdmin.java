package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class SystemAdmin extends User{
	
	private boolean def;
	

	public SystemAdmin() {
		super();
	}



	public SystemAdmin(boolean def) {
		super();
		this.def = def;
	}



	public boolean isDef() {
		return def;
	}



	public void setDef(boolean def) {
		this.def = def;
	}


	
	
	
}
