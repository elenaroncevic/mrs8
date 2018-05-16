package app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Auditorium;
import app.model.Movie;
import app.model.Projection;
import app.model.Reservation;
import app.model.Seat;
import app.model.Ticket;
import app.repository.CinemaRepository;
import app.repository.MovieRepository;
import app.repository.ProjectionRepository;
import app.repository.ReservationRepository;
import app.repository.TicketRepository;

@Service
public class RegisteredUserService {
	
	@Autowired
	ProjectionRepository projRep;
	
	@Autowired 
	TicketRepository ticketRep;
	
	@Autowired
	MovieRepository movieRep;
	
	@Autowired
	CinemaRepository cinemaRep;
	
	@Autowired
	ReservationRepository reservRep;
	
	public Projection getProjection(Long id) {
		return projRep.findOne(id);
	}
	
	public Projection getProjFromTicket(Long id) {
		Ticket tick = getTicket(id);
		return tick.getProjection();
	}
	
	public Movie getMovie(Long id) {
		Ticket tick = getTicket(id);
		return tick.getProjection().getMovie();
	}
	
	public Ticket getTicket(Long id) {
		return ticketRep.findOne(id);
	}
	
	public List<Movie> getMoviesFromCinema(Long id){
		Set<Auditorium> projs = cinemaRep.findOne(id).getAuditoriums();
		List<Movie> retValue = new ArrayList<Movie>();
		for(Auditorium aud : projs) {
			for(Projection proj : aud.getProjections()) {
				if(!retValue.contains(proj.getMovie())) {
					retValue.add(proj.getMovie());
				}
			}
		}
		return retValue;
	}
	
	public Auditorium getAudFromProjection(Long id){		
		return projRep.findOne(id).getAuditorium();
	}

	public List<List<Seat>> getSeatsFromProjection(Long id){
		Projection proj = getProjection(id);
		Set<Seat> setSeats = proj.getAuditorium().getSeats();
		List<Seat> listSeats = new ArrayList<Seat>();
		List<Seat> unavailableSeats = new ArrayList<Seat>();
		
		//all and sectored seats
		
		for(Seat s : setSeats) {
			listSeats.add(s);
			if(s.getSector()!=null) {
				unavailableSeats.add(s);
			}
		}
		
		//reserved seats
		for(Ticket tick : proj.getTickets()) {
			unavailableSeats.add(tick.getSeat());
		}
		
		List<List<Seat>> retValue=new ArrayList<List<Seat>>();
		retValue.add(listSeats);
		retValue.add(unavailableSeats);
		return retValue;
	}
	
	public List<Seat> getSeatsFromReservation(Long id) {
		Reservation reserv = reservRep.findOne(id);
		List<Seat> retValue = new ArrayList<Seat>();
		System.out.println("da li da da li da da da");
		for(Ticket tick : reserv.getTickets()) {
			retValue.add(tick.getSeat());
			System.out.println(tick.getSeat().getId());
		}
		return retValue;
	}
}
