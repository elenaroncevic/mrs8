package app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.dto.SeatDTO;
import app.model.PointScale;
import app.model.Projection;
import app.model.RegisteredUser;
import app.model.Reservation;
import app.model.Row;
import app.model.Seat;
import app.model.Ticket;
import app.model.Visitation;
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


@Transactional
@Service
public class SeatsReservationService {

	
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


	//needed
	@Transactional(readOnly = true)
	public List<List<SeatDTO>> getSeatsFromProjection(Long id){
		
		Projection proj = projRep.findOne(id);
		List<SeatDTO> listSeats = new ArrayList<SeatDTO>();
		List<SeatDTO> unavailableSeats = new ArrayList<SeatDTO>();
		
		//all and sectored seats
		for(Row r : proj.getAuditorium().getRows()) {
			if(r.getActive()==0) {
				continue;
			}
			for(Seat s : r.getSeats()) {
				listSeats.add(new SeatDTO(s));
				if(s.getActive().equals(Seat.SeatState.Active)) {
					if(s.getSector()!=null) {
						unavailableSeats.add(new SeatDTO(s));
					}	
				}else {
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
	
	//needed
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Long makeReservation(String email, Long projId, String seats, int num) {
		
		RegisteredUser user = (RegisteredUser) userRep.findOne(email);
		
		Reservation reserv = new Reservation();
		reserv.setState(Reservation.ReservationState.Active);
		reserv.setBuyer(user);
		Integer n = user.getNumOfReservations()+1;
		user.setNumOfReservations(n);
		String id="1";
		
		PointScale ps = pointScaleRepository.findOne(Long.parseLong(id));
		
		user.setUserMedal(ps.getCopper(), ps.getSilver(), ps.getGolden());
		
		userRep.save(user);
		
		Reservation saved = reservRep.save(reserv);
		
		String[] seatsList = seats.split(",");
		Ticket.TicketState state = Ticket.TicketState.Requested;
		for(int i = 0;i<seatsList.length;i++) {
			if(i==num) {
				state = Ticket.TicketState.Active;
			}
			Ticket tick = new Ticket();
			tick.setState(state);
			
			//add for later;will be sent with the emails
			if(tick.getState().equals(Ticket.TicketState.Requested) || i==num) {
				Visitation visit = new Visitation(tick, Visitation.VisitationType.Wait);
				RegisteredUserService.visits.add(visit);
			}
			
			tick.setProjection(projRep.findOne(projId));
			
			tick.setReservation(saved);
			
			tick.setSeat(seatRep.findOne(Long.parseLong(seatsList[i])));
			
			ticketRep.save(tick);	
		}
		return saved.getId();
	}
}
