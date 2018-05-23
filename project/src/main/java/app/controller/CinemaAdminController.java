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
import app.dto.QuickTicketDTO;
import app.model.Cinema;
import app.model.Movie;
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
	
	@RequestMapping(value="/refreshUser/{id:.+}", method=RequestMethod.GET)
	public ResponseEntity<User> refreshUser(@PathVariable("id") String id) {
		User user =cinemaAdminService.getUser(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	@RequestMapping(value="/refreshCinema/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cinema> refreshCinema(@PathVariable("id") Long id) {
		Cinema cinema =cinemaAdminService.getCinema(id);
		return new ResponseEntity<>(cinema, HttpStatus.OK);
	}
	@RequestMapping(value="/disableSeat/{id}", method=RequestMethod.POST)
	public ResponseEntity<Void> disableSeat(@PathVariable("id") Long id) {
		boolean reserved =cinemaAdminService.disableSeat(id);
		if(reserved)
			return new ResponseEntity<>( HttpStatus.OK);
		else
			System.out.println("dobro");
			return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
	}
	
	@RequestMapping(value="/addProjection/{id}/{aid}/{year}/{month}/{day}/{hours}/{minutes}/{price}/{movie}", method=RequestMethod.POST)
	public ResponseEntity<Void> addProjection(
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
		boolean ok = cinemaAdminService.addProjection(id, aid, c,price,movie);
		if(ok)
			return new ResponseEntity<>( HttpStatus.OK);
		else
			return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
	}
	
	@RequestMapping(value="/addSeat/{row}/{kol}", method=RequestMethod.POST)
	public ResponseEntity<Void> addSeat(
			@PathVariable("row") Long row_id,
			@PathVariable("kol") Integer kol){
		boolean ok = cinemaAdminService.addSeat(row_id,kol);
		if(ok)
			return new ResponseEntity<>( HttpStatus.OK);
		else
			return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
	}
	
	@RequestMapping(value="/removeSeat/{row}/{kol}", method=RequestMethod.POST)
	public ResponseEntity<Void> removeSeat(
			@PathVariable("row") Long row_id,
			@PathVariable("kol") Integer kol){
		boolean ok = cinemaAdminService.removeSeat(row_id,kol);
		if(ok)
			return new ResponseEntity<>( HttpStatus.OK);
		else
			return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
	}
	
	@RequestMapping(value="/addRow/{rowNum}/{audi}", method=RequestMethod.POST)
	public ResponseEntity<Void> addRow(
			@PathVariable("rowNum") int kol,
			@PathVariable("audi") Long audi_id){
		boolean ok = cinemaAdminService.addRow(kol,audi_id);
		if(ok)
			return new ResponseEntity<>( HttpStatus.OK);
		else
			return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
	}
	
	@RequestMapping(value="/removeRow/{row}/{audi}", method=RequestMethod.POST)
	public ResponseEntity<Void> addRow(
			@PathVariable("row") Long row_id,
			@PathVariable("audi") Long audi_id){
		boolean ok = cinemaAdminService.removeRow(row_id,audi_id);
		if(ok)
			return new ResponseEntity<>( HttpStatus.OK);
		else
			return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
	}
	
	@RequestMapping(value="/qtGet/{cinemaId}", method=RequestMethod.GET)
	public ResponseEntity<List<QuickTicketDTO>> getQTs(
			@PathVariable("cinemaId") Long cid){
		List<QuickTicketDTO> qts = cinemaAdminService.qtGet(cid);
		return new ResponseEntity<>(qts, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/qtAdd/{proj_id}/{seat_id}/{discount}", method=RequestMethod.POST)
	public ResponseEntity<Void> qtAdd(
			@PathVariable("proj_id") Long proj_id,
			@PathVariable("seat_id") Long seat_id,
			@PathVariable("discount") Integer discount){
		boolean ok = cinemaAdminService.qtAdd(proj_id,seat_id, discount);
		if(ok)
			return new ResponseEntity<>( HttpStatus.OK);
		else
			return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
	}
	
	@RequestMapping(value="/qtRemove/{qtId}", method=RequestMethod.POST)
	public ResponseEntity<Void> qtAdd(
			@PathVariable("qtId") Long qtId){
		boolean ok = cinemaAdminService.qtRemove(qtId);
		if(ok)
			return new ResponseEntity<>( HttpStatus.OK);
		else
			return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
	}
}


