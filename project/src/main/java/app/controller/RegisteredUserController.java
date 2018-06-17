package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import app.dto.AuditoriumDTO;
import app.dto.CinemaDTO;
import app.dto.FriendshipDTO;
import app.dto.MovieDTO;
import app.dto.ProjectionDTO;
import app.dto.RegisteredUserDTO;
import app.dto.ReservationDTO;
import app.dto.SeatDTO;
import app.model.Movie;
import app.model.Projection;
import app.model.Ticket;
import app.model.User;
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
	public ResponseEntity<List<MovieDTO>> getMoviesFromCinema(@PathVariable Long id){
		List<MovieDTO> projs = regUserService.getMoviesFromCinema(id);
		return new ResponseEntity<>(projs, HttpStatus.OK);
	}
	
	//podlozna brisanju
	/*
	@RequestMapping("/auditorium/{id}")
	public ResponseEntity<Auditorium> getAudFromProjection(@PathVariable Long id){
		Auditorium auds = regUserService.getAudFromProjection(id);
		return new ResponseEntity<>(auds, HttpStatus.OK);
	}*/
	
	@RequestMapping("/seats/{id}")
	public ResponseEntity<List<List<SeatDTO>>> getSeatsFromProjection(@PathVariable Long id){
		List<List<SeatDTO>> seats = regUserService.getSeatsFromProjection(id);
		return new ResponseEntity<>(seats, HttpStatus.OK);
	}
	
	@RequestMapping("/reservseats/{id}")
	public ResponseEntity<List<SeatDTO>> getSeatsFromReservation(@PathVariable Long id){
		List<SeatDTO> seats = regUserService.getSeatsFromReservation(id);
		return new ResponseEntity<>(seats, HttpStatus.OK);
	}
	
	//needed
	@RequestMapping("/projections/{id}/{date}")
	public ResponseEntity<List<ProjectionDTO>> getProjectionsFromMovie(@PathVariable("id") Long id, @PathVariable("date") String date){
		List<ProjectionDTO> projs = regUserService.getProjectionsFromMovie(id, date);
		System.out.println("hm");
		return new ResponseEntity<>(projs, HttpStatus.OK);
	}
	
	@RequestMapping("/auditorium/{id}")
	public ResponseEntity<AuditoriumDTO> getAuditorium(@PathVariable("id") Long id){
		AuditoriumDTO aud = regUserService.getAuditorium(id);
		return new ResponseEntity<>(aud, HttpStatus.OK);
	}
	
	@RequestMapping("/friends/{email:.+}")
	public ResponseEntity<List<RegisteredUserDTO>> getFriends(@PathVariable("email") String email){
		List<RegisteredUserDTO> friends = regUserService.getFriends(email);
		return new ResponseEntity<>(friends, HttpStatus.OK);
	}
	
	@RequestMapping("/people/{email:.+}")
	public ResponseEntity<List<RegisteredUserDTO>> getPeople(@PathVariable("email") String email){
		List<RegisteredUserDTO> friends = regUserService.getPeople(email);
		return new ResponseEntity<>(friends, HttpStatus.OK);
	}
	
	@RequestMapping(value="/makeReservation/{email}/{seatId}/{projId}", method=RequestMethod.POST)
	public ResponseEntity<Long> makeReservation(@PathVariable("email") String email, @PathVariable("seatId") Long seatId, @PathVariable("projId") Long projId){
		Long reserv = regUserService.makeReservation(email, projId, seatId);
		return new ResponseEntity<>(reserv, HttpStatus.OK);
	}
	
	@RequestMapping(value="/makeTicket/{email}/{projId}/{resId}/{seatId}", method=RequestMethod.POST)
	public ResponseEntity<Void> makeTicket(@PathVariable("email") String email, @PathVariable("projId") Long projId, @PathVariable("resId") Long resId, @PathVariable("seatId") Long seatId, WebRequest req){
		regUserService.makeTicket(email, resId, projId, seatId, Ticket.TicketState.Requested, req);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping("/acceptInvitation/{token}")
	public ResponseEntity<Void> acceptInvitation(@PathVariable("token") String token){
		if(regUserService.acceptInvitation(token)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//needed
	@RequestMapping(value="/deleteReservation/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id){
		if(regUserService.deleteReservation(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//needed
	@RequestMapping("/reservations/{email:.+}")
	public ResponseEntity<List<ReservationDTO>> getReservations(@PathVariable("email") String email){
		List<ReservationDTO> reservs = regUserService.getReservations(email);
		return new ResponseEntity<>(reservs, HttpStatus.OK);
	}
	
	@RequestMapping("/history/{email:.+}")
	public ResponseEntity<List<CinemaDTO>> getHistory(@PathVariable("email") String email){
		List<CinemaDTO> reservs = regUserService.getHistory(email);
		return new ResponseEntity<>(reservs, HttpStatus.OK);
	}
	
	@RequestMapping("/editPass/{email}/{oldPass}/{pass}/{pass2}")
	public ResponseEntity<User> editPass(@PathVariable("email") String email, @PathVariable("oldPass") String oldPass, @PathVariable("pass") String pass, @PathVariable("pass2") String pass2){
		User user = regUserService.editPass(email, oldPass, pass, pass2);
		if(user==null) {
			return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	@RequestMapping("/editInfo/{email}/{firstName}/{lastName}/{city}/{phone}")
	public ResponseEntity<User> editInfo(@PathVariable("email") String email, @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, @PathVariable("city") String city, @PathVariable("phone") String phone){
		User user = regUserService.editInfo(email, firstName, lastName, city, phone);
		if(user==null) {
			return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/qtBuy/{qtId}/{email:.+}", method=RequestMethod.POST)
	public ResponseEntity<Void> qtBuy(@PathVariable("qtId") Long qtId, @PathVariable("email") String email){
		boolean ok = regUserService.qtBuy(qtId, email);
		if(ok)
			return new ResponseEntity<>( HttpStatus.OK);
		else
			return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
	}
	@RequestMapping("/friendReq/{email:.+}")
	public ResponseEntity<List<FriendshipDTO>> getRequests(@PathVariable("email") String email){
		List<FriendshipDTO> reqs = regUserService.getRequests(email);
		return new ResponseEntity<> (reqs, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/acceptedReq/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> acceptFriend(@PathVariable("id") Long id){
		regUserService.acceptFriend(id);
		return new ResponseEntity<> (HttpStatus.OK);
	}
	
	@RequestMapping(value = "/declinedReq/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> declineFriend(@PathVariable("id") Long id){
		regUserService.declineFriend(id);
		return new ResponseEntity<> (HttpStatus.OK);
	}
	
	@RequestMapping(value="/addFriend/{userEmail}/{frEmail:.+}", method = RequestMethod.POST)
	public ResponseEntity<Void> addFriend(@PathVariable("userEmail") String userEmail, @PathVariable("frEmail") String frEmail){
		regUserService.addFriend(userEmail, frEmail);
		return new ResponseEntity<> (HttpStatus.OK);
	}
	
	@RequestMapping(value="/removeFriend/{userEmail}/{frEmail:.+}", method = RequestMethod.POST)
	public ResponseEntity<Void> removeFriend(@PathVariable("userEmail") String userEmail, @PathVariable("frEmail") String frEmail){
		regUserService.removeFriend(userEmail, frEmail);
		return new ResponseEntity<> (HttpStatus.OK);
	}
	
	
	
	

	
}
