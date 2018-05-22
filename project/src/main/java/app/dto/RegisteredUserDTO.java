package app.dto;

import app.model.RegisteredUser;

public class RegisteredUserDTO {

	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String city;
	private String phone;
	
	public RegisteredUserDTO() {
		
	}
	
	public RegisteredUserDTO(RegisteredUser reg) {
		this.email=reg.getEmail();
		this.password=reg.getPassword();
		this.firstName=reg.getFirstName();
		this.lastName=reg.getLastName();
		this.city=reg.getCity();
		this.phone=reg.getPhone();
	}

	public RegisteredUserDTO(String email, String password, String firstName, String lastName, String city,
			String phone) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.phone = phone;
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
