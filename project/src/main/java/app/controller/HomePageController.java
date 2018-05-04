package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomePageController {

	@RequestMapping("/home")
	public String home() {
		return "homepage";
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
