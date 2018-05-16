package app.controller;

import java.util.Calendar;
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
import app.model.Projection;
import app.model.User;
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
	
	@RequestMapping(value="/removeProjection/{id}", method=RequestMethod.GET)
	public ResponseEntity<Void> removeProjection(@PathVariable("id") Long id) {
		cinemaAdminService.removeProjection(id);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	@RequestMapping(value="/getMovies", method=RequestMethod.GET)
	public ResponseEntity<List<Movie>> getMovies() {
		List<Movie> movies = cinemaAdminService.getMovies();
		return new ResponseEntity<>(movies, HttpStatus.OK);
	}
	
	@RequestMapping(value="/refreshUser/{id}", method=RequestMethod.GET)
	public ResponseEntity<User> refreshUser(@PathVariable("id") String id) {
		User user =cinemaAdminService.getUser(id);
		System.out.println(user.getEmail()+"\n"+user.getPassword());
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	@RequestMapping(value="/refreshCinema/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cinema> refreshCinema(@PathVariable("id") Long id) {
		Cinema cinema =cinemaAdminService.getCinema(id);
		System.out.println(cinema.getAuditoriums());
		return new ResponseEntity<>(cinema, HttpStatus.OK);
	}
	
	@RequestMapping(value="/addProjection/{id}/{aid}/{year}/{month}/{day}/{hours}/{minutes}/{price}/{movie}", method=RequestMethod.POST)
	public ResponseEntity<Projection> addProjection(
			@PathVariable("id") Long id,
			@PathVariable("aid") Long aid,
			@PathVariable("year")int year,
			@PathVariable("month")int month,
			@PathVariable("day")int day,
			@PathVariable("hours") int hours,
			@PathVariable("minutes") int minutes,
			@PathVariable("price") Double price,
			@PathVariable("movie") Long movie) {
		Calendar c = Calendar.getInstance();
		c.set(year,month,day,hours,minutes);
		Projection p = cinemaAdminService.addProjection(id, aid, c,price,movie);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}
}


