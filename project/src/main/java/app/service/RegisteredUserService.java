package app.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import app.dto.AuditoriumDTO;
import app.dto.FriendshipDTO;
import app.dto.MovieDTO;
import app.dto.ProjectionDTO;
import app.dto.RegisteredUserDTO;
import app.dto.ReservationDTO;
import app.dto.SeatDTO;
import app.dto.VisitationDTO;
import app.model.Auditorium;
import app.model.ConfirmationToken;
import app.model.Friendship;
import app.model.Friendship.FriendshipState;
import app.model.Movie;
import app.model.PointScale;
import app.model.Projection;
import app.model.QuickTicket;
import app.model.RegisteredUser;
import app.model.Reservation;
import app.model.Row;
import app.model.Seat;
import app.model.Ticket;
import app.model.User;
import app.model.Visitation;
import app.model.Visitation.VisitationType;
import app.repository.AuditoriumRepository;
import app.repository.CinemaRepository;
import app.repository.ConfirmationTokenRepository;
import app.repository.FriendshipRepository;
import app.repository.MovieRepository;
import app.repository.PointScaleRepository;
import app.repository.ProjectionRepository;
import app.repository.ReservationRepository;
import app.repository.SeatRepository;
import app.repository.TicketRepository;
import app.repository.UserRepository;
import app.repository.VisitationRepository;

@Service
public class RegisteredUserService {
	
	public static List<Visitation> visits= new ArrayList<Visitation>();
	
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
	private PointScaleRepository pointScaleRepository;
	
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private VisitationRepository visitationRepository;
    
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
	public ProjectionDTO getSingleProjection(Long id) {
		Projection p = projRep.findOne(id);
		if(p==null) {
			return null;
		}else {
			return new ProjectionDTO(p);
		}
	}
	
	public AuditoriumDTO getAuditorium(Long id) {
		Auditorium aud = audRep.findOne(id);
		AuditoriumDTO retValue = new AuditoriumDTO(aud);
		return retValue;
	}
	
	
	
	
	//needed
	public ReservationDTO sendEmails(Long reservId, String emails, HttpServletRequest request) {
		Reservation r = reservRep.findOne(reservId);
		String[] emailsList = emails.split(",");
		int emailCounter = 0;
		
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String base = url.substring(0, url.length() - uri.length() + ctx.length());
		base=base+  "/";
		
		for(Ticket tick : r.getTickets()) {
			if(tick.getState().equals(Ticket.TicketState.Requested)) {
		        String token = UUID.randomUUID().toString();
		        ConfirmationToken ct = new ConfirmationToken();
		        ct.setTicket(tick);
		        ct.setToken(token);
		        String subject = "Invitation to a movie";
		        String text = "Your friend "+r.getBuyer().getFirstName()+" "+r.getBuyer().getLastName()+" wants you to go see a movie with him. Click on the link below if you accept his invitation!";
		        String confirmationUrl = base + "reguser/acceptInvitation.html?token=" + token;
		        String declinationUrl = base + "reguser/declineInvitation.html?token=" +token;
		        
		        ctRep.save(ct);
		        
		        SimpleMailMessage eMail = new SimpleMailMessage();
		        eMail.setTo(emailsList[emailCounter]);
		        
		        Visitation visit = visits.get(emailCounter);
		        visit.setVisitor((RegisteredUser)userRep.findOne(emailsList[emailCounter]));
		        visitationRepository.save(visit);
		        
		        emailCounter++;
		        eMail.setSubject(subject);
		        eMail.setText(text+"\n\n"+confirmationUrl+"\nIf you don't want to go see this movie, click this link: \n\n"+declinationUrl+"\n\n\nBest regards, \\nTheCinTeam");
		        mailSender.send(eMail);
			}
		}
		visits = new ArrayList<Visitation>();
        return sendEmail(reservId);
		
	}
	
	//needed
	public ReservationDTO sendEmail(Long resId) {
		Reservation r = reservRep.findOne(resId);
		
		String seats = "";
		for(Ticket tick : r.getTickets()) {
			seats=seats+tick.getSeat().getRow().getNumber()+tick.getSeat().getNumber()+",";
		}
		seats = seats.substring(0, seats.length()-1);
		
		
        SimpleMailMessage eMail = new SimpleMailMessage();
        eMail.setTo(r.getBuyer().getEmail());
        eMail.setSubject("Information about reservation");
        
        Projection proj = r.getTickets().iterator().next().getProjection();
        String text = "Dear, " +r.getBuyer().getFirstName()+" " +r.getBuyer().getLastName()+",\n\n you have successfully reserved ticket(s) using our app. Details of your purchase:\n"; 
        text=text+"\nPlace: "+proj.getAuditorium().getCinema().getName();
        text=text+"\nShow: " +proj.getMovie().getTitle();
        text=text+"\nDate and time: " + proj.getDate();
        text=text+"\nAuditorium: "+proj.getAuditorium().getNumber();
        text=text+"\nNumber of tickets: "+r.getTickets().size();
        text=text+"\nSeats: "+seats;      
        
        double price = r.getPrice();
		int discount = getDiscount(r.getBuyer().getUserMedal());
		double discountPrice = price-price*discount*0.01;
		
        
        text=text+"\nPrice: " + price+" (with discount: "+discountPrice+")";
        text=text+"\n\nThank you for using TheCinApp!\n\n\nBest regards, \nTheCinTeam";
        eMail.setText(text);
        mailSender.send(eMail);
        
        return new ReservationDTO(r, discount);
	}
	
	//needed
	public int getDiscount(RegisteredUser.Medal medal) {
		PointScale ps = pointScaleRepository.findOne(Long.parseLong("1"));
		int discount=0;
		
		if(medal.equals(RegisteredUser.Medal.Copper)) {
			discount = ps.getCopper_discount();
		}else if(medal.equals(RegisteredUser.Medal.Silver)) {
			discount=ps.getSilver_discount();
		}else if(medal.equals(RegisteredUser.Medal.Golden)) {
			discount=ps.getGolden_discount();
		}
		return discount;
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
			if(proj.getActive()==0) {
				continue;
			}
			lista = proj.getDate().split(" ");
			if(lista[0].equals(comp)) {
				retValue.add(new ProjectionDTO(proj));
			}
		}
		return retValue;
	}
	
	
	//needed
	public List<RegisteredUserDTO> getFriends(String email){
		List<RegisteredUserDTO> retValue = new ArrayList<RegisteredUserDTO>();
		RegisteredUser current = (RegisteredUser) userRep.findOne(email);
		
		for(Friendship friendship : current.getFriendsAccepted()) {
			System.out.println(friendship.getSender().getEmail());
			if(friendship.getState()==Friendship.FriendshipState.Accepted) {
				retValue.add(new RegisteredUserDTO(friendship.getSender()));
			}
		}
		for(Friendship friendship : current.getFriendsAdded()) {
			System.out.println(friendship.getFriend().getEmail());
			if(friendship.getState()==Friendship.FriendshipState.Accepted) {
				retValue.add(new RegisteredUserDTO(friendship.getFriend()));
			}
		}
		return retValue;
	}
	
	//needed
	public boolean acceptInvitation(String token) {
		ConfirmationToken ct = ctRep.findByToken(token);
		if(ct==null) {
			return false;
		}
		Ticket tick = ct.getTicket();
		if(tick.getState()==Ticket.TicketState.Cancelled || tick.getState()==Ticket.TicketState.Inactive) {
			return false;
		}
		tick.setState(Ticket.TicketState.Active);
		ticketRep.save(tick);
		ctRep.delete(ct);
		return true;
	}
	
	//needed
	public boolean declineInvitation(String token) {
		ConfirmationToken ct = ctRep.findByToken(token);
		if(ct==null) {
			return false;
		}
		Ticket tick = ct.getTicket();
		if(tick.getState()==Ticket.TicketState.Cancelled || tick.getState()==Ticket.TicketState.Inactive) {
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
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
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
			
			RegisteredUser ru = reserv.getBuyer();
			Integer i = ru.getNumOfReservations()-1;
			ru.setNumOfReservations(i);
			
			PointScale ps = pointScaleRepository.findOne(Long.parseLong("1"));
			ru.setUserMedal(ps.getCopper(), ps.getSilver(), ps.getGolden());
			
			userRep.save(ru);
			
			for(Ticket tick : reserv.getTickets()) {
				if(tick.getState().equals(Ticket.TicketState.Requested)) {
					ConfirmationToken ct = ctRep.findByTicket(tick);
					ctRep.delete(ct);
				}
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
				retValue.add(new ReservationDTO(r, getDiscount(r.getBuyer().getUserMedal())));
			}
		}
		return retValue;
	}
	
	//needed
	public List<VisitationDTO> getHistory(String email){
		RegisteredUser ru = (RegisteredUser) userRep.findOne(email);
		List<VisitationDTO> retValue = new ArrayList<VisitationDTO>();
		for(Visitation visit : ru.getVisits()) {
			if(visit.isValid()) {
				System.out.println("tue");
				retValue.add(new VisitationDTO(visit));
			}
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
		
		for(RegisteredUser fr : friends) {
			System.out.println(fr.getEmail() +"    friend1");
		}
		
		
		boolean found=false;
		List<RegisteredUserDTO> retValue = new ArrayList<RegisteredUserDTO>();
		for(User person : ppl){
			if(person.getActivated().equals("yes") && person instanceof RegisteredUser && !email.equals(person.getEmail())) {
				RegisteredUserDTO potential = new RegisteredUserDTO((RegisteredUser)person);
				if(friends.isEmpty()) {
					retValue.add(potential);
					continue;
				}
				found=false;
					for(RegisteredUser friend : friends) {
						if(friend.getEmail().equals(potential.getEmail())) {
							found=true;
							break;
						}
					}
				if(!found) {
					retValue.add(potential);
				}
			}
		}
		return retValue;
	}
	
	//needed
	public List<FriendshipDTO> getRequests(String email){
		List<FriendshipDTO> retValue = new ArrayList<FriendshipDTO>();
		RegisteredUser ru = (RegisteredUser) userRep.findOne(email);
		for(Friendship fr : ru.getFriendsAccepted()) {
			System.out.println(fr.getState());
			if(fr.getState()==FriendshipState.Requested) {
				retValue.add(new FriendshipDTO(fr));
			}
		}
		return retValue;
	}
	
	//needed
	public boolean acceptFriend(Long id) {
		Friendship fr = friendRep.findOne(id);
		fr.setState(Friendship.FriendshipState.Accepted);
		friendRep.save(fr);
		return true;
	}
	
	//needed
	public boolean declineFriend(Long id) {
		Friendship fr = friendRep.findOne(id);
		friendRep.delete(fr);
		return true;
	}
	
	//needed
	public boolean addFriend(String uEmail, String fEmail) {
		Friendship fr = new Friendship();
		fr.setSender((RegisteredUser) userRep.findOne(uEmail));
		fr.setFriend((RegisteredUser) userRep.findOne(fEmail));
		fr.setState(Friendship.FriendshipState.Requested);
		friendRep.save(fr);
		return true;
	}
	
	//needed
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
		friendRep.delete(remove);
		return true;
	}

	public ReservationDTO qtBuy(Long qtId, String userId) {
		
		
		RegisteredUser user = (RegisteredUser)userRep.findOne(userId);
		Reservation res = new Reservation(user, Reservation.ReservationState.Active);
		Reservation saved =reservRep.save(res);

		QuickTicket qt = (QuickTicket)ticketRep.findOne(qtId);
		qt.setState(Ticket.TicketState.Active);
		qt.setReservation(res);
		ticketRep.save(qt);
		Visitation visit = new Visitation(qt, user,VisitationType.Wait );
		visitationRepository.save(visit);
		//visi
		//reservRep.s
		ReservationDTO rdto = new ReservationDTO(saved, qt);
		return rdto;
		
		
	}
	
	
}
