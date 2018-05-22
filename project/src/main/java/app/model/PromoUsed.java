package app.model;




import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.IIOImage;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "PromoUsed")
public class PromoUsed{
	
	@Id
	@GeneratedValue
	@Column(name="puid")
	private Long id;
	
	@Column(name="puendingdate")
	private String endingDate;
	
	@Column(name="puname")
	private String name;
	
	@Column(name="pudescription")
	private String description;
	
	@Column(name="puactivity")
	private String activity;
	
	@Column(name="puprice")
	private Double price;
	
	private String image;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private RegisteredUser owner;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JsonBackReference
	private RegisteredUser buyer;
	
	@OneToMany(mappedBy = "promo", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<Bid> bids = new HashSet<Bid>();

	
	

	public String getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Set<Bid> getbids() {
		return bids;
	}

	public void setbids(Set<Bid> bids) {
		this.bids = bids;
	}

	public RegisteredUser getOwner() {
		return owner;
	}

	public void setOwner(RegisteredUser owner) {
		this.owner = owner;
	}

	public RegisteredUser getBuyer() {
		return buyer;
	}

	public void setBuyer(RegisteredUser buyer) {
		this.buyer = buyer;
	}
	
	
	

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<Bid> getBids() {
		return bids;
	}

	public void setBids(Set<Bid> bids) {
		this.bids = bids;
	}

	

	public PromoUsed(Long id, String endingDate, String name, String description, String activity,
			Double price, String image, RegisteredUser owner, RegisteredUser buyer, Set<Bid> bids) {
		super();
		this.id = id;
		this.endingDate = endingDate;
		this.name = name;
		this.description = description;
		this.activity = activity;
		this.price = price;
		this.image = image;
		this.owner = owner;
		this.buyer = buyer;
		this.bids = bids;
	}

	
	public PromoUsed() {
		super();
	}
	
	
	
}
