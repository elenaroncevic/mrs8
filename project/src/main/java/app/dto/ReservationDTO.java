package app.dto;

import app.model.Projection;
import app.model.QuickTicket;
import app.model.Reservation;
import app.model.Ticket;

public class ReservationDTO {
	private Long id;
	private String title;
	private String date;
	private int ticketNum;
	private String seats;
	private double totalPrice;
	private double discountPrice;
	private boolean isQT;
	
	
	
	public ReservationDTO(Long id, String title, String date, int ticketNum, String seats, double totalPrice,
			double discountPrice, boolean isQT) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
		this.ticketNum = ticketNum;
		this.seats = seats;
		this.totalPrice = totalPrice;
		this.discountPrice = discountPrice;
		this.isQT = isQT;
	}

	public boolean isQT() {
		return isQT;
	}

	public void setQT(boolean isQT) {
		this.isQT = isQT;
	}

	public double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public ReservationDTO() {
		
	}
	
	public ReservationDTO(Reservation reservation, QuickTicket qt) {
		this.id = reservation.getId();
		this.title=qt.getProjection().getMovie().getTitle();
		this.date=qt.getProjection().getDate();
		this.ticketNum=1;
		this.seats=qt.getSeat().getRow().getNumber()+""+qt.getSeat().getNumber();
		this.totalPrice=qt.getProjection().getPrice();
		this.discountPrice=this.totalPrice-this.totalPrice*0.01*qt.getDiscount();
		this.isQT=true;
	}
	
	public ReservationDTO(Reservation reservation, int discount) {
		Projection proj = null;
		String seats="";
		int counter=0;
		
		for(Ticket tick : reservation.getTickets()) {
			if(tick.getState().equals(Ticket.TicketState.Active) || tick.getState().equals(Ticket.TicketState.Requested)) {
				counter=counter+1;
				if(proj==null) {
					proj = tick.getProjection();
					seats = seats+tick.getSeat().getRow().getNumber()+tick.getSeat().getNumber();
					continue;
				}	
				seats = seats + ", " +tick.getSeat().getRow().getNumber()+tick.getSeat().getNumber();
			}
			
		}
		this.isQT=false;
		if(reservation.getTickets().size()==1 && reservation.getTickets().iterator().next() instanceof QuickTicket) {
			this.isQT=true;
		}
		this.id = reservation.getId();
		this.title = proj.getMovie().getTitle();
		this.date = proj.getDate();
		this.ticketNum = reservation.getTickets().size();
		this.seats=seats;
		
		this.totalPrice = counter*proj.getPrice();
		this.discountPrice=this.totalPrice-this.totalPrice*discount*0.01;
	}



	public ReservationDTO(Long id, String title, String date, int ticketNum, String seats, double totalPrice) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
		this.ticketNum = ticketNum;
		this.seats = seats;
		this.totalPrice = totalPrice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
}
