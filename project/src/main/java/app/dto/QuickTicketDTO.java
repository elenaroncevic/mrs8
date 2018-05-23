package app.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import app.model.Projection;
import app.model.QuickTicket;
import app.model.Reservation;
import app.model.Seat;
import app.model.Ticket.TicketState;

public class QuickTicketDTO {
	
	private Integer discount;
	private Double price;
	private Long id;
	private String movieTitle;	
	private Integer seatNumber;
	private Long rowNumber;
	private Integer audiNumber;
	private TicketState state;	
	private String date;
	private String time;
		
	public QuickTicketDTO() {}

	public QuickTicketDTO(Long id, Integer discount, Projection projection, Seat seat, TicketState state) {
		this.discount = discount;
		this.movieTitle = projection.getMovie().getTitle();
		this.date = projection.getDate();
		this.time = projection.getTime();
		this.seatNumber = seat.getNumber();
		this.rowNumber = seat.getRow().getNumber();
		this.audiNumber = seat.getRow().getAuditorium().getNumber();
		this.price = projection.getPrice();
		this.state= state;	
		this.id=id;
	}

	public QuickTicketDTO(QuickTicket qt) {
		this.discount = qt.getDiscount();
		this.movieTitle = qt.getProjection().getMovie().getTitle();
		this.date = qt.getProjection().getDate();
		this.time = qt.getProjection().getTime();
		this.seatNumber = qt.getSeat().getNumber();
		this.rowNumber = qt.getSeat().getRow().getNumber();
		this.audiNumber = qt.getSeat().getRow().getAuditorium().getNumber();
		this.price = qt.getProjection().getPrice();
		this.state= qt.getState();	
		this.id=qt.getId();
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Integer getAudiNumber() {
		return audiNumber;
	}

	public void setAudiNumber(Integer audiNumber) {
		this.audiNumber = audiNumber;
	}

	public TicketState getState() {
		return state;
	}

	public void setState(TicketState state) {
		this.state = state;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
	
	
}
