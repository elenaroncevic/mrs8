package model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.IIOImage;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "PromoUsed")
public class PromoUsed{
	
	@Id
	@GeneratedValue
	@Column(name="puid")
	private int id;
	
	@Column(name="puending")
	private Date ending;
	
	@Column(name="puname")
	private String name;
	
	@Column(name="pudescription")
	private String description;
	
	@Column(name="puimage")
	private IIOImage image;
	
	@Column(name="puactivity")
	private String activity;
	
	@Column(name="puprice")
	private double price;
	
	/* dodaj konstruktore i get/set
	@ManyToOne
	@JoinColumn (name="ruid")
	@JsonBackReference
	@Column (name="puowner")
	private RegisteredUser owner;
	
	@ManyToOne
	@JoinColumn (name="ruid")
	@JsonBackReference
	@Column (name="pubuyer")
	private RegisteredUser buyer;
	
	
	*/
	
	@OneToMany(mappedBy = "promoused") //nisam sigurna da li i zasto je ovo cinema
	private Set<Bid> auditoriums = new HashSet<Bid>();

	public Date getEnding() {
		return ending;
	}

	public void setEnding(Date ending) {
		this.ending = ending;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Set<Bid> getAuditoriums() {
		return auditoriums;
	}

	public void setAuditoriums(Set<Bid> auditoriums) {
		this.auditoriums = auditoriums;
	}
	
	
	
}
