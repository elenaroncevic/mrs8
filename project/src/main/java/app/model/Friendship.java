package app.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Friendship {
	
	public enum FriendshipState{
		Accepted,
		Cancelled,
		Requested
	}
	
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private RegisteredUser sender;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private RegisteredUser friend;
	
	private FriendshipState state;
	
	public Friendship() {
		super();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Friendship(Long id, RegisteredUser sender, RegisteredUser friend, FriendshipState state) {
		super();
		this.id = id;
		this.sender = sender;
		this.friend = friend;
		this.state = state;
	}



	public RegisteredUser getSender() {
		return sender;
	}

	public void setSender(RegisteredUser sender) {
		this.sender = sender;
	}

	public RegisteredUser getFriend() {
		return friend;
	}

	public void setFriend(RegisteredUser friend) {
		this.friend = friend;
	}

	public FriendshipState getState() {
		return state;
	}

	public void setState(FriendshipState state) {
		this.state = state;
	}
	
	
}
