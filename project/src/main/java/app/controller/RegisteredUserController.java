package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.Auditorium;
import app.model.Movie;
import app.model.Projection;
import app.model.Seat;
import app.service.RegisteredUserService;

@RestController
@RequestMapping("/reguser")
public class RegisteredUserController {
	
	@Autowired
	RegisteredUserService regUserService;
	
	@RequestMapping("/projection/{id}")
	public ResponseEntity<Projection> getProjection(@PathVariable Long id){
		Projection projection = regUserService.getProjFromTicket(id);
		return new ResponseEntity<>(projection, HttpStatus.OK);
	}
	
	@RequestMapping("/movie/{id}")
	public ResponseEntity<Movie> getMovie(@PathVariable Long id){
		Movie movie = regUserService.getMovie(id);
		return new ResponseEntity<>(movie, HttpStatus.OK);
	}
	
	@RequestMapping("/movies/{id}")
	public ResponseEntity<List<Movie>> getMoviesFromCinema(@PathVariable Long id){
		List<Movie> projs = regUserService.getMoviesFromCinema(id);
		return new ResponseEntity<>(projs, HttpStatus.OK);
	}
	
	@RequestMapping("/auditorium/{id}")
	public ResponseEntity<Auditorium> getAudFromProjection(@PathVariable Long id){
		Auditorium auds = regUserService.getAudFromProjection(id);
		return new ResponseEntity<>(auds, HttpStatus.OK);
	}
	
	@RequestMapping("/seats/{id}")
	public ResponseEntity<List<List<Seat>>> getSeatsFromProjection(@PathVariable Long id){
		List<List<Seat>> seats = regUserService.getSeatsFromProjection(id);
		return new ResponseEntity<>(seats, HttpStatus.OK);
	}
	
	@RequestMapping("/reservseats/{id}")
	public ResponseEntity<List<Seat>> getSeatsFromReservation(@PathVariable Long id){
		List<Seat> seats = regUserService.getSeatsFromReservation(id);
		return new ResponseEntity<>(seats, HttpStatus.OK);
	}
	


}
