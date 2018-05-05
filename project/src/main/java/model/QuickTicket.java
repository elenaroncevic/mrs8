package model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Table(name="QuickTicket")
public class QuickTicket {
	@Id
	@GeneratedValue
	@Column(name="qid")
	private int id;
	
	@ManyToOne
	@JoinColumn (name="pid")
	@JsonBackReference
	private Projection projection;
	
	@ManyToOne
	@JoinColumn (name="seid")
	@JsonBackReference
	private Seat seat;
	
	private int discount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public QuickTicket(int id, Projection projection, Seat seat, int discount) {
		super();
		this.id = id;
		this.projection = projection;
		this.seat = seat;
		this.discount = discount;
	}

	public QuickTicket() {
		super();
	}
	
}
