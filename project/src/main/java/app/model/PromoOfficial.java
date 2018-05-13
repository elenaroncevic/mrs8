package app.model;

import javax.imageio.IIOImage;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "PromoOfficial")
public class PromoOfficial{
	@Id
	@GeneratedValue
	@Column(name="poid")
	private Long id;
	
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private Cinema cinema;
	
	@Column(name="poname")
	private String name;
	
	@Column(name="podescription")
	private String description;
	
	@Column(name="poactivity")
	private String activity;
	
	@Column(name="poprice")
	private Double price;

	private String image;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private RegisteredUser buyer;
	
	public PromoOfficial() {
		super();
	}
	
	
	

	public PromoOfficial(Long id, Cinema cinema, String name, String description, String activity, Double price,
			String image, RegisteredUser buyer) {
		super();
		this.id = id;
		this.cinema = cinema;
		this.name = name;
		this.description = description;
		this.activity = activity;
		this.price = price;
		this.image = image;
		this.buyer = buyer;
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





	public RegisteredUser getBuyer() {
		return buyer;
	}

	public void setBuyer(RegisteredUser buyer) {
		this.buyer = buyer;
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

	

	
	
}
