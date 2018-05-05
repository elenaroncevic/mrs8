package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="Bid")
public class Bid {
	@Id
	@GeneratedValue
	@Column(name="bid")
	private int id;
	
	@ManyToOne
	@JoinColumn (name="puid")
	@JsonBackReference
	private PromoUsed promo;

	
	/* get/set i konstruktori
	@ManyToOne
	@JoinColumn (name="ruid")
	@JsonBackReference
	private RegisteredUser bidder;*/
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PromoUsed getPromo() {
		return promo;
	}

	public void setPromo(PromoUsed promo) {
		this.promo = promo;
	}
	
}
