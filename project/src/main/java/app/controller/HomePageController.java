package app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class HomePageController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<String> home() {
		return new ResponseEntity<String>("Ikija slaying", HttpStatus.OK);
	}
	
	@RequestMapping("/register")
	public String goToRegister(@RequestParam String register_btn) {
		return "registration";
	}
	
	@RequestMapping("/login")
	public String goToLogin(@RequestParam String login_btn) {
		return login_btn;
	}
	
	@RequestMapping("/fin_register")
	public String registered( @RequestParam("email") String email, @RequestParam("pass") String pass, @RequestParam("pass2") String pass2, @RequestParam("first_name") String first_name, @RequestParam("last_name") String last_name, @RequestParam("city") String city, @RequestParam("phone") String phone) {
		//add
		return "homepage";
	}
	
}
