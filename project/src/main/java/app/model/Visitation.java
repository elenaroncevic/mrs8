package app.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import app.model.Ticket.TicketState;

@Entity
public class Visitation {
	
	public enum VisitationType{
		Active,
		Wait,
		Deleted,
		Canceled
	}
	private VisitationType active;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private RegisteredUser visitor;
	
	@OneToOne
	@MapsId
	private Ticket ticket;
	
	public Visitation() {
		
	}

	public Visitation(Long id, Ticket ticket, RegisteredUser visitor, VisitationType active) {
		super();
		this.id = id;
		this.visitor = visitor;
		this.ticket = ticket;
		this.active = active;
	}
	
	public Visitation( Ticket ticket, RegisteredUser visitor, VisitationType active) {
		super();
		this.visitor = visitor;
		this.ticket = ticket;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public VisitationType getActive() {
		return active;
	}

	public void setActive(VisitationType active) {
		this.active = active;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public RegisteredUser getVisitor() {
		return visitor;
	}

	public void setVisitor(RegisteredUser visitor) {
		this.visitor = visitor;
	}
	
	public boolean isValid(){
		DateFormat df = new SimpleDateFormat();
		Calendar now = Calendar.getInstance();
		try {
			Date proj = df.parse(ticket.getProjection().getDate());	
			if(proj.before(now.getTime()) && ticket.getState() == TicketState.Active){
				return true;
			}
			else{
				return false;
			}
		} catch (ParseException e) {
			System.out.println("catch");
			return false;
		}
	}
	
}
