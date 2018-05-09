package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import app.service.RegistrationService;
import app.service.SystemAdminService;

@RestController
public class SystemAdminController {
	
	@Autowired
	RegistrationService registrationService;
	
	
	
	@RequestMapping(value="/register_new_admin/{admEmail}/{admPass1}/{admType}", method=RequestMethod.POST)
	public ResponseEntity<Void> checkData(@PathVariable("admEmail") String email, @PathVariable("admPass1") String pass1, @PathVariable("admType") String type,  WebRequest req){
		if (registrationService.registrationAdmins(email, pass1, type, req)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
}
