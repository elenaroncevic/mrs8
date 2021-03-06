package app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Ticket {
	
	public enum TicketState{
		Active,
		Inactive,
		Cancelled, 
		Requested
	}
	
	@Id
	@GeneratedValue
	@Column(name="qid")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private Projection projection;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private Seat seat;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private Reservation reservation;
	
	private TicketState state;
	
	public Ticket() {
		
	}


	public Ticket(Long id, Projection projection, Seat seat, Reservation reservation, TicketState state) {
		super();
		this.id = id;
		this.projection = projection;
		this.seat = seat;
		this.reservation = reservation;
		this.state = state;
	}









	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}



	public Reservation getReservation() {
		return reservation;
	}



	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}


	public TicketState getState() {
		return state;
	}


	public void setState(TicketState state) {
		this.state = state;
	}
	
	
}
