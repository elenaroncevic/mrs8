package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class FanZoneAdmin extends User{
	private Boolean first_time;
	private String image;
	
	public FanZoneAdmin() {
		super();
	}
	


	public FanZoneAdmin(Boolean first_time, String image) {
		super();
		this.first_time = first_time;
		this.image = image;
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
