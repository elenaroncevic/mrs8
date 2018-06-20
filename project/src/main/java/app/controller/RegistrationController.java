package app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.service.RegistrationService;

@RestController
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;
	
	@RequestMapping(value="/register/{email}/{pass}/{firstName}/{lastName}/{city}/{phone}", method=RequestMethod.POST)
	public ResponseEntity<Void> checkData(@PathVariable("email") String email, @PathVariable("pass") String pass, @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, @PathVariable("city") String city, @PathVariable("phone") String phone, HttpServletRequest req){
		if (registrationService.registration(email, pass, firstName, lastName, city, phone,req)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	 
	@RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
	public ResponseEntity<Void> confirmRegistration(@RequestParam("token") String token) {
		if(registrationService.confirmRegistration(token)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
