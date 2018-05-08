package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Cinema;
import app.model.User;
import app.service.ListCinemasService;

@RestController
public class CinemaListController {
	@Autowired
	private ListCinemasService listCinemasService;
	
	@RequestMapping(value="/cinemas", method=RequestMethod.GET)
	public ResponseEntity<List<Cinema>> listCinemas() {
		List<Cinema> lc = listCinemasService.list();
		if(lc!=null){
			System.out.println("ovde sam u clc.java!");
			return new ResponseEntity<>(lc,HttpStatus.OK);	}
		else
			return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
	}
}
