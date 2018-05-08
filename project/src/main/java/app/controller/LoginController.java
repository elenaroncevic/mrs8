package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.RegisteredUser;
import app.model.User;
import app.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value="/loguser/{email}/{pass}", method=RequestMethod.POST)
	public ResponseEntity<User> login(@PathVariable("email") String email, @PathVariable("pass") String pass) {
		User user = loginService.findUser(email, pass);
		if(user==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
}
