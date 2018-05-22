package app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import app.dto.AuditoriumDTO;
import app.dto.MovieDTO;
import app.dto.ProjectionDTO;
import app.dto.RegisteredUserDTO;
import app.model.Auditorium;
import app.model.ConfirmationToken;
import app.model.Friendship;
import app.model.Movie;
import app.model.Projection;
import app.model.RegisteredUser;
import app.model.Reservation;
import app.model.Seat;
import app.model.Ticket;
import app.repository.AuditoriumRepository;
import app.repository.CinemaRepository;
import app.repository.ConfirmationTokenRepository;
import app.repository.MovieRepository;
import app.repository.ProjectionRepository;
import app.repository.ReservationRepository;
import app.repository.SeatRepository;
import app.repository.TicketRepository;
import app.repository.UserRepository;

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
	
	@Autowired
	AuditoriumRepository audRep;
	
	@Autowired
	UserRepository userRep;
	
	@Autowired
	SeatRepository seatRep;
	
	@Autowired
	ConfirmationTokenRepository ctRep;
	
    @Autowired
    private JavaMailSender mailSender;
    
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
	
	public List<MovieDTO> getMoviesFromCinema(Long id){
		Set<Auditorium> projs = cinemaRep.findOne(id).getAuditoriums();
		List<MovieDTO> retValue = new ArrayList<MovieDTO>();
		List<Long> keys = new ArrayList<Long>();
		for(Auditorium aud : projs) {
			for(Projection proj : aud.getProjections()) {
				if(!keys.contains(proj.getMovie().getId())) {
					keys.add(proj.getMovie().getId());
					retValue.add(new MovieDTO(proj.getMovie()));
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
		for(Ticket tick : reserv.getTickets()) {
			retValue.add(tick.getSeat());
		}
		return retValue;
	}
	
	public List<ProjectionDTO> getProjectionsFromMovie(Long id){
		Movie movie = movieRep.findOne(id);
		List<ProjectionDTO> retValue = new ArrayList<ProjectionDTO>();
		for(Projection proj : movie.getProjections()) {
			retValue.add(new ProjectionDTO(proj));
		}
		return retValue;
	}
	
	public AuditoriumDTO getAuditorium(Long id) {
		Auditorium aud = audRep.findOne(id);
		AuditoriumDTO retValue = new AuditoriumDTO(aud);
		return retValue;
	}
	
	public List<RegisteredUserDTO> getFriends(String email){
		List<RegisteredUserDTO> retValue = new ArrayList<RegisteredUserDTO>();
		System.out.println(email);
		RegisteredUser current = (RegisteredUser) userRep.findOne(email);
		
		for(Friendship friendship : current.getFriendsAccepted()) {
			if(friendship.getState()==Friendship.FriendshipState.Accepted) {
				retValue.add(new RegisteredUserDTO(friendship.getSender()));
			}
		}
		for(Friendship friendship : current.getFriendsAdded()) {
			if(friendship.getState()==Friendship.FriendshipState.Accepted) {
				retValue.add(new RegisteredUserDTO(friendship.getFriend()));
			}
		}
		return retValue;
	}
	
	public Long makeReservation(String email, Long projId, Long seatId) {
		Reservation reserv = new Reservation();
		reserv.setState(Reservation.ReservationState.Active);
		reserv.setBuyer((RegisteredUser)userRep.findOne(email));
		Reservation saved = reservRep.save(reserv);
		makeTicket(null, saved.getId(), projId, seatId, true, null);
		return saved.getId();
	}
	
	public boolean makeTicket(String email, Long resId, Long projId, Long seatId, boolean acc, WebRequest request) {
		Ticket tick = new Ticket();
		Reservation r = reservRep.findOne(resId);
		tick.setAccepted(acc);
		tick.setProjection(projRep.findOne(projId));
		tick.setReservation(r);
		tick.setSeat(seatRep.findOne(seatId));
		Ticket saved = ticketRep.save(tick);
		if(request!=null) {
			String appUrl = request.getContextPath();
	        String token = UUID.randomUUID().toString();
	        ConfirmationToken ct = new ConfirmationToken(token, tick);
	        String subject = "Invitation to a movie";
	        String text = "Your friend "+r.getBuyer().getFirstName()+" "+r.getBuyer().getLastName()+" wants you to go see a movie with him. Click on the link below if you accept his invitation!";
	        String confirmationUrl = appUrl + "/acceptInvitation.html?token=" + token;
	        
	        ctRep.save(ct);
	        
	        SimpleMailMessage eMail = new SimpleMailMessage();
	        eMail.setTo(email);
	        eMail.setSubject(subject);
	        eMail.setText(text+"\n"+"http://localhost:8181" + confirmationUrl);
	        mailSender.send(eMail);
		}
		return true;
	}
	
	public boolean acceptInvitation(String token) {
		ConfirmationToken ct = ctRep.findByToken(token);
		if(ct==null) {
			return false;
		}
		Ticket tick = ct.getTicket();
		tick.setAccepted(true);
		ticketRep.save(tick);
		ctRep.delete(ct);
		return true;
	}
}
