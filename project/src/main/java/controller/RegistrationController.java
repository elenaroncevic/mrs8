package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.RegisteredUser;
import service.RegistrationService;

public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;
	
	@RequestMapping(value="/register", method=RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Void> checkData(@RequestBody RegisteredUser ruser){
		if (registrationService.registration(ruser)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
