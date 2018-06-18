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
	
	
	
	public PointScale() {
		super();
	}


	public PointScale(Long id, int copper, int silver, int golden) {
		super();
		this.id = id;
		this.copper = copper;
		this.silver = silver;
		this.golden = golden;
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
	
	
	
	
}
