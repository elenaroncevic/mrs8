package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Help;
import app.service.RegistrationService;

@RestController
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;
	
	@RequestMapping(value="api/register", method=RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> checkData(@RequestBody Help ruh){
		return new ResponseEntity<String>(ruh.getIme(), HttpStatus.OK);
	}
	
	@RequestMapping(value="api/help", method=RequestMethod.POST)
	public ResponseEntity<Help> check(@RequestBody Help help){
		return new ResponseEntity<Help>(help, HttpStatus.OK);
	}
	
}
