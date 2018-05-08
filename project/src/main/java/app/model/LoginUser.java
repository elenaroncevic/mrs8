package app.model;

public class LoginUser {
	
	private String email;
	private String pass;
	
	public LoginUser() {
		
	}

	public LoginUser(String email, String pass) {
		super();
		this.email = email;
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "LoginUser [email=" + email + ", pass=" + pass + "]";
	}
	
	
}
