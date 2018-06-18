package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class SystemAdmin extends User{
	
	private Boolean def;
	private Boolean first_time;
	private String image;

	public SystemAdmin() {
		super();
	}

	

	public SystemAdmin(Boolean def, Boolean first_time, String image) {
		super();
		this.def = def;
		this.first_time = first_time;
		this.image = image;
	}


	public Boolean isDef() {
		return def;
	}



	public void setDef(Boolean def) {
		this.def = def;
	}

	public Boolean getFirst_time() {
		return first_time;
	}

	public void setFirst_time(Boolean first_time) {
		this.first_time = first_time;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}

	
	
	
	
}
