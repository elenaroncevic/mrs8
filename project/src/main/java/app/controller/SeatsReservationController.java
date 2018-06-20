package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.dto.SeatDTO;
import app.service.SeatsReservationService;

@RestController
@RequestMapping("/seatreserv")
public class SeatsReservationController {
	
	@Autowired
	SeatsReservationService seatReservService;
	
	@RequestMapping("/seats/{id}")
	public ResponseEntity<List<List<SeatDTO>>> getSeatsFromProjection(@PathVariable Long id){
		List<List<SeatDTO>> seats = seatReservService.getSeatsFromProjection(id);
		return new ResponseEntity<>(seats, HttpStatus.OK);
	}
	
	//needed
	@RequestMapping(value="/makeReservation/{email}/{projId}/{seats}/{num}", method=RequestMethod.POST)
	public ResponseEntity<Long> makeReservation(@PathVariable("email") String email, @PathVariable("projId") Long projId, @PathVariable("seats") String seats, @PathVariable("num") int num){
		Long i = seatReservService.makeReservation(email, projId, seats, num);
		return new ResponseEntity<>(i, HttpStatus.OK);

		
	}
	


}
