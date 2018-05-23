package app.dto;

public class BidDTO {
	private Long id;
	private Long promo_id;
	private Double price;
	private String bidder_email;
	
	public BidDTO() {
		super();
	}

	public BidDTO(Long id, Long promo_id, Double price, String bidder_email) {
		super();
		this.id = id;
		this.promo_id = promo_id;
		this.price = price;
		this.bidder_email = bidder_email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPromo_id() {
		return promo_id;
	}

	public void setPromo_id(Long promo_id) {
		this.promo_id = promo_id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getBidder_email() {
		return bidder_email;
	}

	public void setBidder_email(String bidder_email) {
		this.bidder_email = bidder_email;
	}
	
	
	
}
