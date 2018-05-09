package app.model;

import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class Friendship {
	
	public enum FriendshipState{
		Accepted,
		Cancelled,
		Requested
	}

	@ManyToOne
	@JsonBackReference
	private RegisteredUser sender;
	@ManyToOne
	@JsonBackReference
	private RegisteredUser friend;
	
	private FriendshipState state;
	
	public Friendship() {
		super();
	}

	public Friendship(RegisteredUser sender, RegisteredUser friend, FriendshipState state) {
		super();
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
