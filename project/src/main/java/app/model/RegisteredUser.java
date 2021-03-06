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
	public enum Medal{
		Copper,
		Silver,
		Golden, 
		None
	}
	
	private Integer numOfReservations;
	private Medal userMedal;
	
	@Column(name="ruFirstName")
	private String firstName;
	@Column(name="ruLastName")
	private String lastName;
	@Column(name="ruCity")
	private String city;
	@Column(name="ruPhone")
	private String phone;
	
	@OneToMany(mappedBy = "sender",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonManagedReference
	private Set<Friendship> friendsAdded=new HashSet<Friendship>();
	
	@OneToMany(mappedBy = "friend",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonManagedReference
	private Set<Friendship> friendsAccepted = new HashSet<Friendship>();
	
	
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
	
	@OneToMany (mappedBy = "visitor",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JsonManagedReference
	private Set<Visitation> visits = new HashSet<Visitation>();
	
	
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


	


	public RegisteredUser(String firstName, String lastName, String city, String phone, Set<Friendship> friendsAdded,
			Set<Friendship> friendsAccepted, Set<CinemaRate> cinemaRates, Set<MovieRate> movieRates,
			Set<Reservation> reservations, Set<Bid> bids, Set<PromoOfficial> promoOfficials, Set<PromoUsed> promoUsed,
			Set<Visitation> visits) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.phone = phone;
		this.friendsAdded = friendsAdded;
		this.friendsAccepted = friendsAccepted;
		this.cinemaRates = cinemaRates;
		this.movieRates = movieRates;
		this.reservations = reservations;
		this.bids = bids;
		this.promoOfficials = promoOfficials;
		this.promoUsed = promoUsed;
		this.visits = visits;
	}
	
	


	public RegisteredUser(Integer numOfReservations, Medal userMedal, String firstName, String lastName, String city,
			String phone, Set<Friendship> friendsAdded, Set<Friendship> friendsAccepted, Set<CinemaRate> cinemaRates,
			Set<MovieRate> movieRates, Set<Reservation> reservations, Set<Bid> bids, Set<PromoOfficial> promoOfficials,
			Set<PromoUsed> promoUsed, Set<Visitation> visits) {
		super();
		this.numOfReservations = numOfReservations;
		this.userMedal = userMedal;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.phone = phone;
		this.friendsAdded = friendsAdded;
		this.friendsAccepted = friendsAccepted;
		this.cinemaRates = cinemaRates;
		this.movieRates = movieRates;
		this.reservations = reservations;
		this.bids = bids;
		this.promoOfficials = promoOfficials;
		this.promoUsed = promoUsed;
		this.visits = visits;
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


	public Set<Friendship> getFriendsAdded() {
		return friendsAdded;
	}


	public void setFriendsAdded(Set<Friendship> friendsAdded) {
		this.friendsAdded = friendsAdded;
	}


	public Set<Friendship> getFriendsAccepted() {
		return friendsAccepted;
	}


	public void setFriendsAccepted(Set<Friendship> friendsAccepted) {
		this.friendsAccepted = friendsAccepted;
	}


	public Set<Visitation> getVisits() {
		return visits;
	}


	public void setVisits(Set<Visitation> visits) {
		this.visits = visits;
	}


	public Integer getNumOfReservations() {
		return numOfReservations;
	}


	public void setNumOfReservations(Integer numOfReservations) {
		this.numOfReservations = numOfReservations;
	}


	public Medal getUserMedal() {
		return userMedal;
	}

	

	public void setUserMedal(Medal userMedal) {
		this.userMedal = userMedal;
	}


	public void setUserMedal(int copper, int silver, int golden) {
		if (this.numOfReservations < copper){
			this.userMedal=Medal.None;
		}else if (copper <= this.numOfReservations && this.numOfReservations < silver){
			this.userMedal=Medal.Copper;
		}else if (silver <= this.numOfReservations && this.numOfReservations < golden){
			this.userMedal=Medal.Silver;
		}else{
			this.userMedal=Medal.Golden;
		}
	}
	
	
	
}
