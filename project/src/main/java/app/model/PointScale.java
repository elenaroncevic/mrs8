package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PointScale")
public class PointScale {
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	private int copper;
	private int silver;
	private int golden;
	
	private int copper_discount;
	private int silver_discount;
	private int golden_discount;
	
	
	public PointScale() {
		super();
	}


	public PointScale(Long id, int copper, int silver, int golden, int copper_discount, int silver_discount,
			int golden_discount) {
		super();
		this.id = id;
		this.copper = copper;
		this.silver = silver;
		this.golden = golden;
		this.copper_discount = copper_discount;
		this.silver_discount = silver_discount;
		this.golden_discount = golden_discount;
	}


	public int getCopper() {
		return copper;
	}



	public void setCopper(int copper) {
		this.copper = copper;
	}



	public int getSilver() {
		return silver;
	}



	public void setSilver(int silver) {
		this.silver = silver;
	}



	public int getGolden() {
		return golden;
	}



	public void setGolden(int golden) {
		this.golden = golden;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getCopper_discount() {
		return copper_discount;
	}


	public void setCopper_discount(int copper_discount) {
		this.copper_discount = copper_discount;
	}


	public int getSilver_discount() {
		return silver_discount;
	}


	public void setSilver_discount(int silver_discount) {
		this.silver_discount = silver_discount;
	}


	public int getGolden_discount() {
		return golden_discount;
	}


	public void setGolden_discount(int golden_discount) {
		this.golden_discount = golden_discount;
	}
	
	
	
	
}
