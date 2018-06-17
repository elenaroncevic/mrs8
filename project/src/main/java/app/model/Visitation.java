package app.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Visitation {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private Cinema cinema;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private RegisteredUser visitor;
	
	public Visitation() {
		
	}

	public Visitation(Long id, Cinema cinema, RegisteredUser visitor) {
		super();
		this.id = id;
		this.cinema = cinema;
		this.visitor = visitor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public RegisteredUser getVisitor() {
		return visitor;
	}

	public void setVisitor(RegisteredUser visitor) {
		this.visitor = visitor;
	}
	
	
	
}
