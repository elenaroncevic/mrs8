package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="RegUsers")
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
	private Set<CinemaRate> cinemaRates = new HashSet<CinemaRate>();
	
	@OneToMany(mappedBy = "voter")
	private Set<MovieRate> movieRates = new HashSet<MovieRate>();
	
	@OneToMany(mappedBy = "buyer",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<QuickTicket> quickTickets = new HashSet<QuickTicket>();
	
	@OneToMany (mappedBy = "bidder",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@Column(name="rubids")
	private Set<Bid> bids = new HashSet<Bid>();
	
	@OneToMany (mappedBy = "buyer",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@Column(name="rupromoO")
	private Set<PromoOfficial> promoOfficials = new HashSet<PromoOfficial>();
	
	@OneToMany (mappedBy = "buyer",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@Column(name="rpromoU")
	private Set<PromoUsed> promoUsed = new HashSet<PromoUsed>();
	
	private String activated;
	
	public RegisteredUser(){
		super();
	}


	public RegisteredUser(String firstName, String lastName, String city, String phone,
			Set<CinemaRate> cinemaRates, Set<MovieRate> movieRates, Set<QuickTicket> quickTickets, Set<Bid> bids,
			Set<PromoOfficial> promoOfficials, Set<PromoUsed> promoUsed, String activated) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.phone = phone;
		this.cinemaRates = cinemaRates;
		this.movieRates = movieRates;
		this.quickTickets = quickTickets;
		this.bids = bids;
		this.promoOfficials = promoOfficials;
		this.promoUsed = promoUsed;
		this.activated = activated;
	}




	public Set<QuickTicket> getQuickTickets() {
		return quickTickets;
	}

	public void setQuickTickets(Set<QuickTicket> quickTickets) {
		this.quickTickets = quickTickets;
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
