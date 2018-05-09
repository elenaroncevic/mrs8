package app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class RegisteredUser extends User {
	
	@Column(name="ruFirstName")
	private String firstName;
	@Column(name="ruLastName")
	private String lastName;
	@Column(name="ruCity")
	private String city;
	@Column(name="ruPhone")
	private String phone;
	
	@OneToMany(mappedBy = "voter",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonManagedReference
	private Set<CinemaRate> cinemaRates = new HashSet<CinemaRate>();
	
	@OneToMany(mappedBy = "voter")
	@JsonManagedReference
	private Set<MovieRate> movieRates = new HashSet<MovieRate>();
	
	@OneToMany(mappedBy = "buyer",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonManagedReference
	private Set<Reservation> reservations = new HashSet<Reservation>();
	
	@OneToMany (mappedBy = "bidder",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonManagedReference
	private Set<Bid> bids = new HashSet<Bid>();
	
	@OneToMany (mappedBy = "buyer",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonManagedReference
	private Set<PromoOfficial> promoOfficials = new HashSet<PromoOfficial>();
	
	@OneToMany (mappedBy = "buyer",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonManagedReference
	private Set<PromoUsed> promoUsed = new HashSet<PromoUsed>();
	
	
	
	
	public RegisteredUser(){
		super();
	}


	public Set<MovieRate> getMovieRates() {
		return movieRates;
	}


	public void setMovieRates(Set<MovieRate> movieRates) {
		this.movieRates = movieRates;
	}


	public Set<Bid> getBids() {
		return bids;
	}


	public void setBids(Set<Bid> bids) {
		this.bids = bids;
	}


	public Set<PromoOfficial> getPromoOfficials() {
		return promoOfficials;
	}


	public void setPromoOfficials(Set<PromoOfficial> promoOfficials) {
		this.promoOfficials = promoOfficials;
	}


	public Set<PromoUsed> getPromoUsed() {
		return promoUsed;
	}


	public void setPromoUsed(Set<PromoUsed> promoUsed) {
		this.promoUsed = promoUsed;
	}


	public RegisteredUser(String firstName, String lastName, String city, String phone, Set<CinemaRate> cinemaRates,
			Set<MovieRate> movieRates, Set<Reservation> quickTickets, Set<Bid> bids, Set<PromoOfficial> promoOfficials,
			Set<PromoUsed> promoUsed) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.phone = phone;
		this.cinemaRates = cinemaRates;
		this.movieRates = movieRates;
		this.reservations = quickTickets;
		this.bids = bids;
		this.promoOfficials = promoOfficials;
		this.promoUsed = promoUsed;
	}


	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}


	public Set<CinemaRate> getCinemaRates() {
		return cinemaRates;
	}

	public void setCinemaRates(Set<CinemaRate> cinemaRates) {
		this.cinemaRates = cinemaRates;
	}

	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
