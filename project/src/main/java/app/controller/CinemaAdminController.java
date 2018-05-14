package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Cinema;
import app.service.CinemaAdminService;

@RestController
public class CinemaAdminController {
	@Autowired
	private CinemaAdminService cinemaAdminService;
	
	@RequestMapping(value="/changeBasic/{id}/{name}/{location}/{description}", method=RequestMethod.POST)
	public ResponseEntity<Cinema> changeBasic(@PathVariable("id") Long id,@PathVariable("name") String name,@PathVariable("location") String location,
			@PathVariable("description") String description) {
		Cinema cinema = cinemaAdminService.editCinemaBasic(id, name, location, description);
		return new ResponseEntity<>( cinema, HttpStatus.OK);
	}
}


