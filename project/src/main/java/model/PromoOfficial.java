package model;

import javax.imageio.IIOImage;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	private int id;
	
	
	@ManyToOne
	@JoinColumn (name="cid")
	@JsonBackReference
	@Column (name="pocinema")
	private CinemaAdmin cinema;
	
	@Column(name="poname")
	private String name;
	
	@Column(name="podescription")
	private String description;
	
	@Column(name="poimage")
	private IIOImage image;
	
	@Column(name="poactivity")
	private String activity;
	
	@Column(name="poprice")
	private double price;

	
	/* dodaj konstuktore i get/set
	@ManyToOne
	@JoinColumn (name="ruid")
	@JsonBackReference
	@Column (name="pobuyer")
	private RegisteredUser buyer;*/
	
	public PromoOfficial() {
		super();
	}





	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public CinemaAdmin getCinema() {
		return cinema;
	}


	public void setCinema(CinemaAdmin cinema) {
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





	public IIOImage getImage() {
		return image;
	}





	public void setImage(IIOImage image) {
		this.image = image;
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


	

	
	
	
	
}
