package app.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PromoUsedDTO {
	private Long id;
	private String ending_date;
	private String ending_time;
	private String name;
	private String description;
	private String activity;
	private double price;
	private String owner_email;
	private String buyer_email;
	private String image;
	
	//ove liste se mozda izbace
	private List<Long> bid_id = new ArrayList<Long>();
	private List<String> bid_email = new ArrayList<String>();
	private List<Double> bid_price = new ArrayList<Double>();
	
	public PromoUsedDTO() {
		super();
	}



	


	public PromoUsedDTO(Long id, String ending_date, String ending_time, String name, String description,
			String activity, double price, String owner_email, String buyer_email, String image, List<Long> bid_id,
			List<String> bid_email, List<Double> bid_price) {
		super();
		this.id = id;
		this.ending_date = ending_date;
		this.ending_time = ending_time;
		this.name = name;
		this.description = description;
		this.activity = activity;
		this.price = price;
		this.owner_email = owner_email;
		this.buyer_email = buyer_email;
		this.image = image;
		this.bid_id = bid_id;
		this.bid_email = bid_email;
		this.bid_price = bid_price;
	}






	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public String getEnding_date() {
		return ending_date;
	}






	public void setEnding_date(String ending_date) {
		this.ending_date = ending_date;
	}






	public String getEnding_time() {
		return ending_time;
	}






	public void setEnding_time(String ending_time) {
		this.ending_time = ending_time;
	}






	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOwner_email() {
		return owner_email;
	}

	public void setOwner_email(String owner_email) {
		this.owner_email = owner_email;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}


	


	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public List<Long> getBid_id() {
		return bid_id;
	}





	public void setBid_id(List<Long> bid_id) {
		this.bid_id = bid_id;
	}





	public List<String> getBid_email() {
		return bid_email;
	}





	public void setBid_email(List<String> bid_email) {
		this.bid_email = bid_email;
	}





	public List<Double> getBid_price() {
		return bid_price;
	}





	public void setBid_price(List<Double> bid_price) {
		this.bid_price = bid_price;
	}



	
	
	
	
}
