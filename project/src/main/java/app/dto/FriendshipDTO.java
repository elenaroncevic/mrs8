package app.dto;

import app.model.Friendship;
import app.model.Friendship.FriendshipState;

public class FriendshipDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private Friendship.FriendshipState state;
	
	public FriendshipDTO() {
		
	}
	
	public FriendshipDTO(Friendship fr) {
		this.id=fr.getId();
		this.firstName=fr.getSender().getFirstName();
		this.lastName = fr.getSender().getLastName();
		this.state = fr.getState();
	}

	public FriendshipDTO(Long id, String firstName, String lastName, FriendshipState state) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Friendship.FriendshipState getState() {
		return state;
	}

	public void setState(Friendship.FriendshipState state) {
		this.state = state;
	}
	
	

}
