package app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Reservation {
	
	public enum ReservationState{
		Active,
		Cancelled,
		Inactive
	}

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private RegisteredUser buyer;
	
	@OneToMany(mappedBy = "reservation",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonManagedReference
	private Set<Ticket> tickets = new HashSet<Ticket>();
	
	private ReservationState state;
	
	public Reservation() {
		
	}

	public Reservation(Long id, RegisteredUser buyer, Set<Ticket> tickets, ReservationState rState) {
		super();
		this.id = id;
		this.buyer = buyer;
		this.tickets = tickets;
		this.state = rState;
	}
	public Reservation( RegisteredUser buyer, ReservationState rState) {
		super();
		this.buyer = buyer;
		this.state = rState;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUser getBuyer() {
		return buyer;
	}

	public void setBuyer(RegisteredUser buyer) {
		this.buyer = buyer;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

	public ReservationState getState() {
		return state;
	}

	public void setState(ReservationState state) {
		this.state = state;
	}
	
	public double getPrice() {
		double retValue=0;
		int counter = 0;
		for(Ticket tick : this.tickets) {
			if(counter==0) {
				retValue=tick.getProjection().getPrice();
			}
			if(tick.getState().equals(Ticket.TicketState.Active) || tick.getState().equals(Ticket.TicketState.Requested)) {
				counter=counter++;
			}
		}
			
		return retValue*counter;
	}
	
	
}
