package app.model;

import javax.persistence.Entity;

@Entity
public class QuickTicket extends Ticket{
	
	private Integer discount;

	public QuickTicket(Long id, Projection projection, Seat seat, Reservation reservation, Ticket.TicketState state, Integer discount) {
		super(id, projection, seat, reservation, state);
		this.discount=discount;
	}


	public QuickTicket(Integer discount) {
		super();
		this.discount = discount;
	}




	public QuickTicket() {
		super();
	}



	public Integer getDiscount() {
		return discount;
	}



	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	
}
