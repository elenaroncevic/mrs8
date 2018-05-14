package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.Movie;
import app.model.Projection;
import app.service.RegisteredUserService;

@RestController
@RequestMapping("/reguser")
public class RegisteredUserController {
	
	@Autowired
	RegisteredUserService regUserService;
	
	@RequestMapping("/projection/{id}")
	public ResponseEntity<Projection> getProjection(@PathVariable Long id){
		Projection projection = regUserService.getProjection(id);
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
	public ResponseEntity<String> getAudFromProjection(@PathVariable Long id){
		String projs = regUserService.getAudFromProjection(id);
		return new ResponseEntity<>(projs, HttpStatus.OK);
	}

}
