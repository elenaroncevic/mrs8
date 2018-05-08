package app.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import app.dto.RegisteredUserDTO;
import app.model.RegisteredUser;
import app.model.VerificationToken;
import app.service.RegistrationService;

@RestController
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;
	
	@RequestMapping(value="/register/{email}/{pass}/{firstName}/{lastName}/{city}/{phone}", method=RequestMethod.POST)
	public ResponseEntity<Void> checkData(@PathVariable("email") String email, @PathVariable("pass") String pass, @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, @PathVariable("city") String city, @PathVariable("phone") String phone, WebRequest req){
		if (registrationService.registration(email, pass, firstName, lastName, city, phone,req)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	 
	@RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
	public ResponseEntity<Void> confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
	    VerificationToken verificationToken = registrationService.getVerificationToken(token);
	    RegisteredUser user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if(verificationToken==null || (verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    user.setActivated("yes"); 
	    registrationService.saveRegisteredUser(user); 
	    return new ResponseEntity<>(HttpStatus.OK); 
	}
}