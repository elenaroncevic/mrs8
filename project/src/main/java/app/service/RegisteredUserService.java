package app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Auditorium;
import app.model.Movie;
import app.model.Projection;
import app.model.Ticket;
import app.repository.CinemaRepository;
import app.repository.MovieRepository;
import app.repository.ProjectionRepository;
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
	
	public Projection getProjection(Long id) {
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


}
