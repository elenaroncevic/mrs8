package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.dto.MovieDTO;
import app.model.Cinema;
import app.model.Movie;
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
	/*
	@RequestMapping(value="/getProjections/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<Movie>> cinemaMovies(@PathVariable("id") Long id) {
		List<Movie> movies = cinemaAdminService.getCinemaMovies(id);
		return new ResponseEntity<>( movies, HttpStatus.OK);
	}*/
	
	@RequestMapping(value="/getProjectionMovie/{id}", method=RequestMethod.GET)
	public ResponseEntity<MovieDTO> projectionMovie(@PathVariable("id") Long id) {
		MovieDTO movie = new MovieDTO(cinemaAdminService.getProjectionMovie(id));
		return new ResponseEntity<>( movie, HttpStatus.OK);
	}
}


