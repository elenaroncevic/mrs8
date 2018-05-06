package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FanZoneAdmin")
public class FanZoneAdmin extends User{
	
		
	public FanZoneAdmin() {
		super();
	}
	
	
	
	public FanZoneAdmin(int id, String email, String password) {
		super();
		
	}	
}
