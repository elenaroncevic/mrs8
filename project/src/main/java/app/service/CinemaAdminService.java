package app.service;
 
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
import app.model.CinemaAdmin;
import app.model.Movie;
import app.model.Projection;
import app.model.QuickTicket;
import app.model.Reservation.ReservationState;
import app.model.Row;
import app.model.Seat;
import app.model.Ticket;
import app.model.Ticket.TicketState;
import app.model.User;
import app.model.Visitation;
import app.repository.AuditoriumRepository;
import app.repository.CinemaRepository;
import app.repository.MovieRepository;
import app.repository.ProjectionRepository;
import app.repository.RowRepository;
import app.repository.SeatRepository;
import app.repository.TicketRepository;
import app.repository.UserRepository;
import app.repository.VisitationRepository;

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
	
	@Autowired
	private VisitationRepository visitationRepository;
	
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
		Projection p = projectionRepository.findOne(id);
		Calendar now = Calendar.getInstance();
		Calendar projCal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		try {
			projCal.setTime(df.parse(p.getDate()));
			System.out.println("isparsirao");
			if(now.after(projCal)){
				System.out.println("prosao datum");
				return null;
				
			}
			else{
				System.out.println("sve ok");
				return p.getMovie();
			}
		} catch (ParseException e) {
			System.out.println("catch");
			return null;
		}
		 
	}
	
	public User getUser(String id) {
		return userRepository.findOne(id);
		 
	}
	
	public Cinema getCinema(Long id) {
		Cinema ret = cinemaRepository.findOne(id);
		return ret;
		 
	}
	public void removeProjection(Long id) {
		Projection p = projectionRepository.findOne(id);
		p.setActive(0);
		projectionRepository.save(p);		 
	}
	public List<Movie> getMovies(Long id) {
		List<Movie> movies = movieRepository.findAll();
		List<Movie> ret = new ArrayList<Movie>();
		for(Movie movie : movies){
			if(movie.getCinema().getId() == id){
				ret.add(movie);
			}
		}
		return ret;
		
	}
	public boolean addProjection(Long id, Long aid, Calendar projCalendar, Double price, Long movieId) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, 5);
		if (now.getTime().after(projCalendar.getTime())){
			return false;
		}
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Auditorium audi = audiRepository.findOne(aid);
		Movie movie = movieRepository.findOne(movieId);
		for(Projection proj : audi.getProjections()){
			//System.out.println(": ");
			if(proj.getActive()==1){
				Calendar pc = Calendar.getInstance();
				try {
					pc.setTime(df.parse(proj.getDate()));
					if(!dateOverlap(projCalendar, movie.getDuration(), pc, proj.getMovie().getDuration())){
						return false;
					}				
				} catch (ParseException e) {
					return false;
				}
			}
		}			
		Projection p = new Projection();
		p.setActive(1);
		p.setMovie(movie);
		p.setAuditorium(audi);
		p.setTime(df.format(projCalendar.getTime()));
		p.setDate(df.format(projCalendar.getTime()));
		p.setPrice(price);
		projectionRepository.save(p);
		return true;
	}
	public boolean disableSeat(Long id) {
		Seat seat = seatRepository.findOne(id);
		for(Ticket t : seat.getTickets()){
			if(t.getState()==Ticket.TicketState.Requested || t.getReservation().getState()==ReservationState.Active ){
				return false;
			}
		}
		if(seat.active == Seat.SeatState.Active){
			seat.setActive(Seat.SeatState.Disabled);
		}
		else if(seat.active == Seat.SeatState.Disabled){
			seat.setActive(Seat.SeatState.Active);
		}
		seatRepository.save(seat);
		return true;
		
	}
	public boolean addSeat(Long row_id, Integer kol) {
		//System.out.println(3);
		Row row = rowRepository.findOne(row_id);
		int num = 1;
		for(Seat s : row.getSeats()){
			if(s.getActive()!=Seat.SeatState.Deleted)
				num++;
			for(Ticket t : s.getTickets()){
				if(t.getState()==TicketState.Requested || t.getReservation().getState()==ReservationState.Active ){
					return false;
				}
			}
		}
		
		
		for(int i=0; i< kol; i++){
			Seat newSeat =new Seat( row.getAuditorium(),row,num+i, Seat.SeatState.Active );
			seatRepository.save(newSeat);
		}
		return true;		
	}
	
	public boolean removeSeat(Long row_id, Integer kol) {
		Row row = rowRepository.findOne(row_id);
		int num = 0;
		for(Seat s : row.getSeats()){
			if(s.getActive()!=Seat.SeatState.Deleted){
				num++;
			}
			for(Ticket t : s.getTickets()){
				if(t.getState()==TicketState.Requested || t.getReservation().getState()==ReservationState.Active ){
					return false;
				}
			}
		}	
		if(kol<num){
			List<Seat> seats =seatRepository.findByNumberBetween(num-kol+1,num+1);
			for(Seat s: seats){
				if(s.getRow().getId()==row.getId()){
					s.setActive(Seat.SeatState.Deleted);
					seatRepository.save(s);
				}
				
			}
			return true;
		}
		else
			return false;
	}
	public boolean addRow(int kol, Long audi_id) {
		int i = 0;
		long num=0;
		Auditorium audi = audiRepository.findOne(audi_id);
		Row row = new Row();
		row.setAuditorium(audi);
		for(Row r: audi.getRows()){
			if(r.getActive()==1){
				num++;
			}
		}
		row.setNumber(num+1);
		row.setActive(1);
		rowRepository.save(row);
		for( i = 1; i<kol+1; i++){
			Seat newSeat =new Seat( audi,row,i, Seat.SeatState.Active );
			seatRepository.save(newSeat);
		}
		return true;
	}
	
	public boolean removeRow(Long row_id, Long audi_id) {
		Row row = rowRepository.findOne(row_id);
		for(Seat s : row.getSeats()){			
			for(Ticket t : s.getTickets()){
				if(t.getState()==TicketState.Requested || t.getReservation().getState()==ReservationState.Active ){
					return false;
				}
			}
		}
		Auditorium audi = audiRepository.findOne(audi_id);
		for(Row r : audi.getRows() )
			if(r.getNumber()>row.getNumber())
				r.setNumber(r.getNumber()-1);
		for(Seat s: row.getSeats()){
			s.setActive(Seat.SeatState.Deleted);
			seatRepository.save(s);
		}
		row.setActive(0);
		rowRepository.save(row);
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
		for(Ticket t: seat.getTickets()){
			if(t.getState()==Ticket.TicketState.Requested || t.getReservation().getState() == ReservationState.Active){
				Projection seatProj =t.getProjection();
				if(!projOverlap(proj,seatProj)){
					return false;
				}	
			}
		}
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
	public void addMovie(Long id, String title, String director, String description, String actors, int duration,
			String genre, String image) {
		Cinema cinema = cinemaRepository.findOne(id);
		Movie movie = new Movie(cinema,title,director,description,actors,duration,genre,image);
		movieRepository.save(movie);
		
	}
	
	public User changePass(String email, String pass){
		CinemaAdmin user =(CinemaAdmin) userRepository.findOne(email);
		user.setPassword(pass);
		user.setFirst_time(false);
		
		user = userRepository.save(user);
		return user;
	}
	public boolean projOverlap(Projection p1, Projection p2){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Calendar p1start = Calendar.getInstance();
		Calendar p2start = Calendar.getInstance();
		Calendar p1end = Calendar.getInstance();
		Calendar p2end = Calendar.getInstance();
		try {
			p1start.setTime(df.parse(p1.getDate()));			
			p2end.setTime(df.parse(p2.getDate()));
			return dateOverlap( p1start,p1.getMovie().getDuration(), p2start, p2.getMovie().getDuration() );	
		} catch (ParseException e) {
			System.out.println("catch");
			return false;
		}
	}
	
	public boolean dateOverlap(Calendar p1start, int duration1, Calendar p2start, int duration2){
		Calendar p1end = Calendar.getInstance();
		Calendar p2end = Calendar.getInstance();
		p1end.setTime(p1start.getTime());
		p2end.setTime(p2start.getTime());
		p1end.add(Calendar.MINUTE, duration1);
		p2end.add(Calendar.MINUTE, duration2);
		

		if((p2start.after(p1start) && p1end.after(p2start)) ||
			(p2end.after(p1start) && p1end.after(p2end)) ||
			(p1start.after(p2start)&& p2end.after(p1end))){
			System.out.println("overlap");
			return false;
		}
		else{
			return true;
		}
	}
	public List<List<Object>> attGetDaily(Long cid, int dtype, int year, int month, int day) {
		System.out.println(dtype);
		if(dtype!=1 && dtype!=4){
			if(dtype==2 || dtype==5){
				return attGetWeekly(cid,dtype,year,month,day);
			}
			else {
				return attGetMonthly(cid,dtype,year,month,day);
			}
		}
		else{
			System.out.println("1");
			Calendar help = Calendar.getInstance();
			Calendar begin = Calendar.getInstance();
			begin.set(Calendar.HOUR_OF_DAY, 0);
			begin.set(Calendar.MINUTE,0);
			begin.set(Calendar.SECOND, 0);
			Calendar now = Calendar.getInstance();
			if(year!=0 && month!=0 && day!=0){
				begin.set(year,month,day);
				now.set(year, month, day);
			}
			Calendar end = Calendar.getInstance();
			end.setTime(begin.getTime());
			end.add(Calendar.DAY_OF_MONTH, 1);
			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			List<List<Object>> ret = new ArrayList<List<Object>>();
			ArrayList<String> vreme = new ArrayList<String>();
			ArrayList<Integer> broj = new ArrayList<Integer>();
			for(int i = 0; i<24; i++){
				ArrayList<Object> inner = new ArrayList<Object>();
				help.set(Calendar.HOUR_OF_DAY,i);
				help.set(Calendar.MINUTE,0);
				help.set(Calendar.SECOND, 0);
				inner.add(0, df.format(help.getTime()));
				inner.add(1, 0.0);
				inner.add(2,0.0);
				ret.add(inner);
			}

			for(Visitation v : visitationRepository.findAll()){
				if(v.getTicket().getSeat().getAuditorium().getCinema().getId() == cid){
					System.out.println("legalna visita");
					try {
						help.setTime(df2.parse(v.getTicket().getProjection().getDate()));	
						System.out.println("____\n"+df2.format(help.getTime()));
						System.out.println(df2.format(begin.getTime())+" - "+df2.format(end.getTime())+"\n____");
						if(help.before(end.getTime()) && help.after(begin.getTime()));
						System.out.println("taj dan");
							for(int i = 0; i < 24; i++){
								now.set(Calendar.HOUR_OF_DAY, i);
								now.set(Calendar.MINUTE,0);
								now.set(Calendar.SECOND, 0);
								double a =(Double)ret.get(i).get(1);
								double b = (Double)ret.get(i).get(2);
								if(!dateOverlap(help,v.getTicket().getProjection().getMovie().getDuration(),now,60)){
									a=a+1;
								}
								if(help.after(now)&&dtype==4){
									now.add(Calendar.HOUR_OF_DAY, 1);
									if(help.before(now)){
										if(v.getTicket() instanceof QuickTicket){
											QuickTicket q = (QuickTicket)v.getTicket();
											b=b+(q.getProjection().getPrice()-((q.getDiscount()/100.0)*q.getProjection().getPrice()));
										}
										else{
											b=b+v.getTicket().getProjection().getPrice();													
										}

									}
								}
								ret.get(i).set(1, a);
								ret.get(i).set(2, b);
							}
					} catch (ParseException e) {
						System.out.println("catch");
						return null;
					}
				}
			}
			System.out.println("vraca");
			return ret;
		}
	}
	
	
	public List<List<Object>> attGetWeekly(Long cid, int dtype, int year, int month, int day) {
		Calendar help = Calendar.getInstance();
		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		//now.add(Calendar.DAY_OF_MONTH, -1);
		now.set(Calendar.MINUTE,0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.HOUR_OF_DAY,0);
	
		help.setFirstDayOfWeek(Calendar.MONDAY);
		now.setFirstDayOfWeek(Calendar.MONDAY);
		begin.setFirstDayOfWeek(Calendar.MONDAY);
		end.setFirstDayOfWeek(Calendar.MONDAY);
	
		begin.set(Calendar.HOUR_OF_DAY, 0);
		begin.set(Calendar.MINUTE,0);
		begin.set(Calendar.SECOND, 0);
		if(year!=0 && month!=0 && day!=0){
			begin.set(year,month,day);
			now.set(year, month, day);
		}
		System.out.println("dan: "+begin.get(Calendar.DAY_OF_WEEK));
		begin.add(Calendar.DAY_OF_MONTH, -(begin.get(Calendar.DAY_OF_WEEK)));
		end.setTime(begin.getTime());
		end.add(Calendar.DAY_OF_MONTH, 7);
		
		DateFormat df = new SimpleDateFormat("EEE");
		DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		List<List<Object>> ret = new ArrayList<List<Object>>();
		ArrayList<String> vreme = new ArrayList<String>();
		ArrayList<Integer> broj = new ArrayList<Integer>();
		for(int i = 0; i<7; i++){
			ArrayList<Object> inner = new ArrayList<Object>();
			help.set(Calendar.DAY_OF_WEEK, i);		
			inner.add(0, df.format(help.getTime()));
			inner.add(1, 0.0);
			inner.add(2, 0.0);
			ret.add(inner);
		}
	
		for(Visitation v : visitationRepository.findAll()){
			if(v.getTicket().getSeat().getAuditorium().getCinema().getId() == cid){
				System.out.println("legalna visita");
				try {
					help.setTime(df2.parse(v.getTicket().getProjection().getDate()));	
					System.out.println("____\n"+df2.format(help.getTime()));
					System.out.println(df2.format(begin.getTime())+" - "+df2.format(end.getTime())+"\n____");
					if(help.before(end.getTime()) && help.after(begin.getTime()));
					System.out.println("taj dan");
						for(int i = 0; i < 7; i++){
							now.setTime(begin.getTime());
							now.add(Calendar.DAY_OF_MONTH, i);
							double a =(Double)ret.get(i).get(1);
							double b =(Double)ret.get(i).get(2);
							if(!dateOverlap(help,v.getTicket().getProjection().getMovie().getDuration(),now,60*24)){
								a=a+1;
							}
							if(help.after(now)){
								now.add(Calendar.DAY_OF_MONTH, 1);
								if(help.before(now)){
									if(v.getTicket() instanceof QuickTicket){
										QuickTicket q = (QuickTicket)v.getTicket();
										b=b+(q.getProjection().getPrice()-((q.getDiscount()/100.0)*q.getProjection().getPrice()));
									}
									else{
										b=b+v.getTicket().getProjection().getPrice();													
									}
	
								}
							}
							ret.get(i).set(1, a);
							ret.get(i).set(2, b);
						}
				} catch (ParseException e) {
					System.out.println("catch");
					return null;
				}
			}
		}
		System.out.println("vraca");
		return ret;
	}
	
	public List<List<Object>> attGetMonthly(Long cid, int dtype, int year, int month, int day) {
		Calendar help = Calendar.getInstance();
		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		now.set(Calendar.MINUTE,0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.HOUR_OF_DAY,0);
	
		begin.set(Calendar.HOUR_OF_DAY, 0);
		begin.set(Calendar.MINUTE,0);
		begin.set(Calendar.SECOND, 0);
		if(year!=0 && month!=0 && day!=0){
			begin.set(year,month,day);
			now.set(year, month, day);
		}
		System.out.println("dan: "+begin.get(Calendar.DAY_OF_WEEK));
		begin.set(Calendar.DAY_OF_MONTH, 1);
		end.setTime(begin.getTime());
		end.add(Calendar.MONTH, 1);
		
		Calendar last = Calendar.getInstance();
		last.setTime(end.getTime());
		last.add(Calendar.DAY_OF_MONTH, -1);
		int numDays = last.get(Calendar.DAY_OF_MONTH);
		
		DateFormat df = new SimpleDateFormat("d");
		DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		List<List<Object>> ret = new ArrayList<List<Object>>();
		ArrayList<String> vreme = new ArrayList<String>();
		ArrayList<Integer> broj = new ArrayList<Integer>();
		for(int i = 1; i<=numDays; i++){
			ArrayList<Object> inner = new ArrayList<Object>();
			help.set(Calendar.DAY_OF_MONTH, i);		
			inner.add(0, df.format(help.getTime()));
			inner.add(1, 0.0);
			inner.add(2, 0.0);
			ret.add(inner);
		}
	
		for(Visitation v : visitationRepository.findAll()){
			if(v.getTicket().getSeat().getAuditorium().getCinema().getId() == cid){
				System.out.println("legalna visita");
				try {
					help.setTime(df2.parse(v.getTicket().getProjection().getDate()));	
					System.out.println("____\n"+df2.format(help.getTime()));
					System.out.println(df2.format(begin.getTime())+" - "+df2.format(end.getTime())+"\n____");
					if(help.before(end.getTime()) && help.after(begin.getTime()));
					System.out.println("taj dan");
						for(int i = 0; i < numDays; i++){
							now.setTime(begin.getTime());
							now.add(Calendar.DAY_OF_MONTH, i);
							double a =(Double)ret.get(i).get(1);
							double b =(Double)ret.get(i).get(2);
							if(!dateOverlap(help,v.getTicket().getProjection().getMovie().getDuration(),now,60*24)){
								a=a+1;
							}
							if(help.after(now)){
								now.add(Calendar.DAY_OF_MONTH, 1);
								if(help.before(now)){
									if(v.getTicket() instanceof QuickTicket){
										QuickTicket q = (QuickTicket)v.getTicket();
										b=b+(q.getProjection().getPrice()-((q.getDiscount()/100.0)*q.getProjection().getPrice()));
									}
									else{
										b=b+v.getTicket().getProjection().getPrice();													
									}
	
								}
							}
							ret.get(i).set(1, a);
							ret.get(i).set(2, b);
						}
				} catch (ParseException e) {
					System.out.println("catch");
					return null;
				}
			}
		}
		System.out.println("vraca");
		return ret;
	}
}
