package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Movie;
import app.model.Projection;
import app.model.Ticket;
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

}
