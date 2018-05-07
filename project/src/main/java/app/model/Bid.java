package app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="Bid")
public class Bid {
	@Id
	@GeneratedValue
	@Column(name="bid")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private PromoUsed promo;

	@Column (name = "bprice")
	private double price;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private RegisteredUser bidder;
	
	
	
	public RegisteredUser getBidder() {
		return bidder;
	}

	public void setBidder(RegisteredUser bidder) {
		this.bidder = bidder;
	}

	public Long getId() {
		return id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PromoUsed getPromo() {
		return promo;
	}

	public void setPromo(PromoUsed promo) {
		this.promo = promo;
	}

	public Bid(Long id, PromoUsed promo, double price, RegisteredUser bidder) {
		super();
		this.id = id;
		this.promo = promo;
		this.price = price;
		this.bidder = bidder;
	}

	public Bid() {
		super();
	}
	
	
}
