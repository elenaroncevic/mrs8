package app.dto;

public class PromoOfficialDTO {
	private Long id;
	private Long cinema_id;
	private String cinema_name;
	private String cinema_location;
	private String name;
	private String description;
	private String activity;
	private double price;
	private String image;
	private String buyer_email;
	
	public PromoOfficialDTO() {
		super();
	}

	

	public PromoOfficialDTO(Long id, Long cinema_id, String cinema_name, String cinema_location, String name,
			String description, String activity, double price, String image, String buyer_email) {
		super();
		this.id = id;
		this.cinema_id = cinema_id;
		this.cinema_name = cinema_name;
		this.cinema_location = cinema_location;
		this.name = name;
		this.description = description;
		this.activity = activity;
		this.price = price;
		this.image = image;
		this.buyer_email = buyer_email;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCinema_id() {
		return cinema_id;
	}

	public void setCinema_id(Long cinema_id) {
		this.cinema_id = cinema_id;
	}

	public String getCinema_name() {
		return cinema_name;
	}

	public void setCinema_name(String cinema_name) {
		this.cinema_name = cinema_name;
	}

	public String getCinema_location() {
		return cinema_location;
	}

	public void setCinema_location(String cinema_location) {
		this.cinema_location = cinema_location;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}



	public String getBuyer_email() {
		return buyer_email;
	}



	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	
	
	
	
}
