package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class FanZoneAdmin extends User{
	private Boolean first_time;
	
	public FanZoneAdmin() {
		super();
	}
	
	

	public FanZoneAdmin(Boolean first_time) {
		super();
		this.first_time = first_time;
	}



	public Boolean getFirst_time() {
		return first_time;
	}

	public void setFirst_time(Boolean first_time) {
		this.first_time = first_time;
	}

	
	
	
	
}
