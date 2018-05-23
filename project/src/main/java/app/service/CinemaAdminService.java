package app.service;
 
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dto.QuickTicketDTO;
import app.model.Auditorium;
import app.model.Cinema;
import app.model.Movie;
import app.model.Projection;
import app.model.QuickTicket;
import app.model.Reservation.ReservationState;
import app.model.Row;
import app.model.Seat;
import app.model.Ticket;
import app.model.Ticket.TicketState;
import app.model.User;
import app.repository.AuditoriumRepository;
import app.repository.CinemaRepository;
import app.repository.MovieRepository;
import app.repository.ProjectionRepository;
import app.repository.RowRepository;
import app.repository.SeatRepository;
import app.repository.TicketRepository;
import app.repository.UserRepository;

@Service
public class CinemaAdminService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private CinemaRepository cinemaRepository;
	
	@Autowired
	private ProjectionRepository projectionRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	@Autowired
	private RowRepository rowRepository;
	
	@Autowired
	private AuditoriumRepository audiRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	public Cinema editCinemaBasic(Long id, String name, String location, String description){
		Cinema cinema = cinemaRepository.findOne(id);
		cinema.setName(name);
		cinema.setLocation(location);
		cinema.setDescription(description);
		cinemaRepository.save(cinema);
		return cinema;
	}
	/*
	public List<Movie> getCinemaMovies(Long id){
		Set<Auditorium> projs = cinemaRepository.findOne(id).getAuditoriums();
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
	
	public String getAudFromProjection(Long id){		
		return "Room:"+projectionRepository.findOne(id).getAuditorium().getId();
	}
*/
	public Movie getProjectionMovie(Long id) {
		return projectionRepository.findOne(id).getMovie();
		 
	}
	
	
	
	public User getUser(String id) {
		return userRepository.findOne(id);
		 
	}
	
	public Cinema getCinema(Long id) {
		return cinemaRepository.findOne(id);
		 
	}
	public void removeProjection(Long id) {
		projectionRepository.delete(id);		 
	}
	public List<Movie> getMovies() {
		return movieRepository.findAll();
		
	}
	public boolean addProjection(Long id, Long aid, Calendar projCalendar, Double price, Long movieId) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, 5);
		if (now.getTime().after(projCalendar.getTime())){
			//System.out.println("nasao");
			return false;
		}
		Date date = new Date(projCalendar.getTimeInMillis());
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		String s = df.format(date);
		System.out.println();
		String time = df.format(date.getTime());
		String d = date.toString();
		//System.out.println("s: "+s);
		//System.out.println("d: "+d);

		Projection p = new Projection( s, price);
		Movie movie = movieRepository.findOne(movieId);
		p.setMovie(movie);
		Cinema cinema= cinemaRepository.findOne(id);
		//System.out.println(id);
		for(Auditorium auditorium : cinema.getAuditoriums()){
			//System.out.println(id+" "+aid+" " +auditorium.getId());

			if (auditorium.getId()==aid){
				for(Projection proj : auditorium.getProjections()){
					//System.out.println("proj: "+proj.getId());
					Calendar pc = Calendar.getInstance();
					try {
						pc.setTime(df.parse(proj.getDate()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println(d+"\n"+proj.getDate()+"\n"+pc);
				}
				p.setAuditorium(auditorium);
			}
		}

		p.setTime("2018-05-18 15:15:00");
		projectionRepository.save(p);
		return true;
	}
	public boolean disableSeat(Long id) {
		Seat seat = seatRepository.findOne(id);
		for(Ticket t : seat.getTickets()){
			if(t.getReservation().getState()==ReservationState.Active){
				return false;
			}
		}
		seat.setActive(!seat.getActive());
		seatRepository.save(seat);
		return true;
		
	}
	public boolean addSeat(Long row_id, Integer kol) {
		//System.out.println(3);
		Row row = rowRepository.findOne(row_id);
		for(Seat s : row.getSeats()){
			for(Ticket t : s.getTickets()){
				if(t.getReservation().getState()==ReservationState.Active){
					return false;
				}
			}
		}
		int num = row.getSeats().size()+1;	
		for(int i=0; i< kol; i++){
			Seat newSeat =new Seat( row.getAuditorium(),row,num+i, true );
			seatRepository.save(newSeat);
		}
		return true;		
	}
	
	public boolean removeSeat(Long row_id, Integer kol) {
		Row row = rowRepository.findOne(row_id);
		for(Seat s : row.getSeats()){			
			for(Ticket t : s.getTickets()){
				if(t.getReservation().getState()==ReservationState.Active){
					return false;
				}
			}
		}
		int num = row.getSeats().size();	
		if(kol<num){
			List<Seat> seats =seatRepository.findByNumberBetween(num-kol,num);
			for(Seat s: seats){
				if(s.getRow().getId()==row.getId())
					seatRepository.delete(s);
			}
			return true;
		}
		else
			return false;
	}
	public boolean addRow(int kol, Long audi_id) {
		int i = 0;
		Auditorium audi = audiRepository.findOne(audi_id);
		Row row = new Row();
		row.setAuditorium(audi);
		row.setNumber((long) (audi.getRows().size()+1));
		rowRepository.save(row);

		for( i = 1; i<kol+1; i++){
			Seat newSeat =new Seat( audi,row,i, true );
			seatRepository.save(newSeat);
		}
		return true;
	}
	
	public boolean removeRow(Long row_id, Long audi_id) {
		Row row = rowRepository.findOne(row_id);
		for(Seat s : row.getSeats()){			
			for(Ticket t : s.getTickets()){
				if(t.getReservation().getState()==ReservationState.Active){
					return false;
				}
			}
		}
		Auditorium audi = audiRepository.findOne(audi_id);
		for(Row r : audi.getRows() )
			if(r.getNumber()>row.getNumber())
				r.setNumber(r.getNumber()-1);
		for(Seat s: row.getSeats()){
			seatRepository.delete(s);
		}
		rowRepository.delete(row_id);
		return true;
	}
	
	
	public List<QuickTicketDTO> qtGet(Long cid) {
		Cinema c =cinemaRepository.findOne(cid);
		List<QuickTicketDTO> qts = new ArrayList<QuickTicketDTO>();
		for(Auditorium a : c.getAuditoriums()){
			for(Projection p : a.getProjections()){
				for(Ticket t: p.getTickets()){
					if(t instanceof QuickTicket && t.getState()==TicketState.Requested){
						QuickTicket qt = (QuickTicket)t;
						QuickTicketDTO qtDTO = new QuickTicketDTO(qt);
						qts.add(qtDTO);
					}
				}
			}
		}
		return qts;
	}
	public boolean qtAdd(Long proj_id, Long seat_id, Integer discount) {
		Seat seat = seatRepository.findOne(seat_id);
		Projection proj = projectionRepository.findOne(proj_id);
		QuickTicket qt = new QuickTicket();
		qt.setProjection(proj);
		qt.setSeat(seat);
		qt.setState(TicketState.Requested);
		qt.setDiscount(discount);
		ticketRepository.save(qt);
		return true;
	}
	public boolean qtRemove(Long qtId) {
		ticketRepository.delete(qtId);
		return true;
	}
		
}
