package app.model;

import javax.persistence.Entity;

@Entity
public class QuickTicket extends Reservation{
	
	private int discount;

	public QuickTicket(Long id, Projection projection, Seat seat, RegisteredUser buyer, int discount) {
		super(id, projection, seat, buyer, 1);
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
