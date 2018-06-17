package app.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import app.dto.AuditoriumDTO;
import app.dto.CinemaDTO;
import app.dto.FriendshipDTO;
import app.dto.MovieDTO;
import app.dto.ProjectionDTO;
import app.dto.RegisteredUserDTO;
import app.dto.ReservationDTO;
import app.dto.SeatDTO;
import app.model.Auditorium;
import app.model.ConfirmationToken;
import app.model.Friendship;
import app.model.Friendship.FriendshipState;
import app.model.Movie;
import app.model.Projection;
import app.model.RegisteredUser;
import app.model.Reservation;
import app.model.Row;
import app.model.Seat;
import app.model.Ticket;
import app.model.User;
import app.model.Visitation;
import app.repository.AuditoriumRepository;
import app.repository.CinemaRepository;
import app.repository.ConfirmationTokenRepository;
import app.repository.FriendshipRepository;
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
	FriendshipRepository friendRep;
	
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
	
	//needed
	public List<List<SeatDTO>> getSeatsFromProjection(Long id){
		Projection proj = getProjection(id);
		List<SeatDTO> listSeats = new ArrayList<SeatDTO>();
		List<SeatDTO> unavailableSeats = new ArrayList<SeatDTO>();
		
		//all and sectored seats
		for(Row r : proj.getAuditorium().getRows()) {
			for(Seat s : r.getSeats()) {
				listSeats.add(new SeatDTO(s));
				if(s.getSector()!=null) {
					unavailableSeats.add(new SeatDTO(s));
				}
			}
		}

		
		//reserved seats
		for(Ticket tick : proj.getTickets()) {
			if(tick.getState()==Ticket.TicketState.Active || tick.getState()==Ticket.TicketState.Requested) {
				unavailableSeats.add(new SeatDTO(tick.getSeat()));
			}
		}
		
		List<List<SeatDTO>> retValue=new ArrayList<List<SeatDTO>>();
		retValue.add(listSeats);
		retValue.add(unavailableSeats);
		return retValue;
	}
	
	public List<SeatDTO> getSeatsFromReservation(Long id) {
		Reservation reserv = reservRep.findOne(id);
		List<SeatDTO> retValue = new ArrayList<SeatDTO>();
		for(Ticket tick : reserv.getTickets()) {
			if(tick.getState()==Ticket.TicketState.Active || tick.getState()==Ticket.TicketState.Requested) {
				retValue.add(new SeatDTO(tick.getSeat()));
			}
		}
		return retValue;
	}
	
	//needed
	public List<ProjectionDTO> getProjectionsFromMovie(Long id, String date){
		Movie movie = movieRep.findOne(id);
		String[] lista = null;
		String comp = date.replace(",", "/");
		List<ProjectionDTO> retValue = new ArrayList<ProjectionDTO>();
		for(Projection proj : movie.getProjections()) {
			lista = proj.getDate().split(" ");
			if(lista[0].equals(comp)) {
				retValue.add(new ProjectionDTO(proj));
			}
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
		makeTicket(null, saved.getId(), projId, seatId, Ticket.TicketState.Active, null);
		return saved.getId();
	}
	
	public boolean makeTicket(String email, Long resId, Long projId, Long seatId, Ticket.TicketState state, WebRequest request) {
		Ticket tick = new Ticket();
		Reservation r = reservRep.findOne(resId);
		tick.setState(state);
		tick.setProjection(projRep.findOne(projId));
		tick.setReservation(r);
		tick.setSeat(seatRep.findOne(seatId));
		Ticket saved = ticketRep.save(tick);
		if(request!=null) {
			String appUrl = request.getContextPath();
	        String token = UUID.randomUUID().toString();
	        ConfirmationToken ct = new ConfirmationToken();
	        ct.setTicket(tick);
	        ct.setToken(token);
	        String subject = "Invitation to a movie";
	        String text = "Your friend "+r.getBuyer().getFirstName()+" "+r.getBuyer().getLastName()+" wants you to go see a movie with him. Click on the link below if you accept his invitation!";
	        String confirmationUrl = appUrl + "/acceptInvitation.html?token=" + token;
	        String declinationUrl = appUrl + "/declineInvitation.html?token=" +token;
	        
	        ctRep.save(ct);
	        
	        SimpleMailMessage eMail = new SimpleMailMessage();
	        eMail.setTo(email);
	        eMail.setSubject(subject);
	        eMail.setText(text+"\n"+"http://localhost:8181" + confirmationUrl+"\nIf you don't want to go see this movie, click this link: \n"+declinationUrl);
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
		if(tick.getState()==Ticket.TicketState.Cancelled) {
			return false;
		}
		tick.setState(Ticket.TicketState.Active);
		ticketRep.save(tick);
		ctRep.delete(ct);
		return true;
	}
	
	public boolean declineInvitation(String token) {
		ConfirmationToken ct = ctRep.findByToken(token);
		if(ct==null) {
			return false;
		}
		Ticket tick = ct.getTicket();
		if(tick.getState()==Ticket.TicketState.Cancelled) {
			return false;
		}
		tick.setState(Ticket.TicketState.Cancelled);
		ticketRep.save(tick);
		ctRep.delete(ct);
		return true;
	}
	
	//needed
	public boolean deleteReservation(Long id) {
		Reservation reserv = reservRep.findOne(id);
		String date="";
		for(Ticket tick : reserv.getTickets()) {
			date=tick.getProjection().getDate();
			break;
		}
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		try {
			cal.setTime(df.parse(date));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		now.add(Calendar.MINUTE, -30);
		if(reserv!=null && reserv.getState()==Reservation.ReservationState.Active && now.getTime().before(cal.getTime())) {
			reserv.setState(Reservation.ReservationState.Cancelled);
			reservRep.save(reserv);
			for(Ticket tick : reserv.getTickets()) {
				tick.setState(Ticket.TicketState.Cancelled);
				ticketRep.save(tick);
			}
			return true;
		}
		return false;
	}
	
	//needed
	public List<ReservationDTO> getReservations(String email){
		List<ReservationDTO> retValue = new ArrayList<ReservationDTO>();
		RegisteredUser regU =(RegisteredUser) userRep.findOne(email);
		for(Reservation r : regU.getReservations()) {
			if(r.getState()==Reservation.ReservationState.Active) {
				retValue.add(new ReservationDTO(r));
			}
		}
		return retValue;
	}
	
	public List<CinemaDTO> getHistory(String email){
		RegisteredUser ru = (RegisteredUser) userRep.findOne(email);
		List<CinemaDTO> retValue = new ArrayList<CinemaDTO>();
		for(Visitation visit : ru.getVisits()) {
			retValue.add(new CinemaDTO(visit.getCinema()));
		}
		return retValue;
	}
	
	public User editPass(String email, String oldPass, String pass, String pass2) {
		RegisteredUser ru =(RegisteredUser) userRep.findOne(email);
		if(!pass.equals(pass2) || !oldPass.equals(ru.getPassword())) {
			return null;
		}
		ru.setPassword(pass);
		userRep.save(ru);
		return ru;
	}
	
	public User editInfo(String email, String firstName, String lastName, String city, String phone) {
		RegisteredUser ru =(RegisteredUser) userRep.findOne(email);
		if(ru!=null) {
			ru.setFirstName(firstName);
			ru.setLastName(lastName);
			ru.setCity(city);
			ru.setPhone(phone);
		}
		userRep.save(ru);
		return ru;
	}
	
	public List<RegisteredUserDTO> getPeople(String email){
		List<User> ppl = userRep.findAll();
		RegisteredUser current = (RegisteredUser) userRep.findOne(email);
		List<RegisteredUser> friends = new ArrayList<RegisteredUser>();
		for(Friendship friendship : current.getFriendsAccepted()) {
			if(friendship.getState()==Friendship.FriendshipState.Accepted || friendship.getState()==Friendship.FriendshipState.Requested) {
				friends.add(friendship.getSender());
			}
		}
		for(Friendship friendship : current.getFriendsAdded()) {
			if(friendship.getState()==Friendship.FriendshipState.Accepted || friendship.getState()==Friendship.FriendshipState.Requested) {
				friends.add(friendship.getFriend());
			}
		}
		List<RegisteredUserDTO> retValue = new ArrayList<RegisteredUserDTO>();
		for(User person : ppl){
			if(person instanceof RegisteredUser && !email.equals(person.getEmail())) {
				RegisteredUserDTO potential = new RegisteredUserDTO((RegisteredUser)person);
				if(friends.isEmpty()) {
					retValue.add(potential);
					continue;
				}
					for(RegisteredUser friend : friends) {
						if(!friend.getEmail().equals(potential.getEmail())) {
							retValue.add(potential);
							break;
						}
					}
			}
		}
		return retValue;
	}
	
	public List<FriendshipDTO> getRequests(String email){
		List<FriendshipDTO> retValue = new ArrayList<FriendshipDTO>();
		RegisteredUser ru = (RegisteredUser) userRep.findOne(email);
		for(Friendship fr : ru.getFriendsAccepted()) {
			if(fr.getState()==FriendshipState.Requested) {
				retValue.add(new FriendshipDTO(fr));
			}
		}
		return retValue;
	}
	
	public boolean acceptFriend(Long id) {
		Friendship fr = friendRep.findOne(id);
		fr.setState(Friendship.FriendshipState.Accepted);
		friendRep.save(fr);
		return true;
	}
	
	public boolean declineFriend(Long id) {
		Friendship fr = friendRep.findOne(id);
		fr.setState(Friendship.FriendshipState.Cancelled);
		friendRep.save(fr);
		return true;
	}
	
	public boolean addFriend(String uEmail, String fEmail) {
		Friendship fr = new Friendship();
		fr.setSender((RegisteredUser) userRep.findOne(uEmail));
		fr.setFriend((RegisteredUser) userRep.findOne(fEmail));
		fr.setState(Friendship.FriendshipState.Requested);
		friendRep.save(fr);
		return true;
	}
	
	public boolean removeFriend(String uEmail, String fEmail) {
		RegisteredUser ru = (RegisteredUser) userRep.findOne(uEmail);
		Friendship remove = null;
		for(Friendship fr : ru.getFriendsAccepted()) {
			if(fr.getSender().getEmail().equals(fEmail)) {
				remove = fr;
				break;
			}
		}
		if(remove==null) {
			for(Friendship fr : ru.getFriendsAdded()) {
				if(fr.getFriend().getEmail().equals(fEmail)) {
					remove = fr;
					break;
				}
			}
		}
		remove.setState(Friendship.FriendshipState.Cancelled);
		friendRep.save(remove);
		return true;
	}
	
	
}
