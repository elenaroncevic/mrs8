package app.model;

import javax.persistence.Entity;

@Entity
public class QuickTicket extends Ticket{
	
	private int discount;

	public QuickTicket(Long id, Projection projection, Seat seat, Reservation reservation, int discount) {
		super(id, projection, seat, reservation);
		this.discount=discount;
	}


	public QuickTicket(int discount) {
		super();
		this.discount = discount;
	}




	public QuickTicket() {
		super();
	}



	public int getDiscount() {
		return discount;
	}



	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
}
