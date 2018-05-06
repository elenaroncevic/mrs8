package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RegUsers")
public class RegisteredUser extends User {
	
	@Id
	@GeneratedValue
	@Column(name="ruid")
	private int id;
	@Column(name="ruFirstName")
	private String firstName;
	@Column(name="ruLastName")
	private String lastName;
	@Column(name="ruCity")
	private String city;
	@Column(name="ruPhone")
	private String phone;

	public RegisteredUser(){
		super();
	}

	public RegisteredUser(String firstName, String lastName, String city, String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.phone = phone;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
