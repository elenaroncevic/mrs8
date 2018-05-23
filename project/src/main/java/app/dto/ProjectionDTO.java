package app.dto;

import app.model.Movie;
import app.model.Projection;

public class ProjectionDTO {
	
	private Long id;
	private String date;
	private String time;
	private Double price;
	private Long audId;
	private Integer audNum;
	
	public ProjectionDTO(Projection projection) {
		this.id=projection.getId();
		this.date=projection.getDate();
		this.time=projection.getTime();
		this.price=projection.getPrice();
		this.audId=projection.getAuditorium().getId();
		this.audNum=projection.getAuditorium().getNumber();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getAudId() {
		return audId;
	}

	public void setAudId(Long audId) {
		this.audId = audId;
	}

	public Integer getAudNum() {
		return audNum;
	}

	public void setAudNum(Integer audNum) {
		this.audNum = audNum;
	}

	public ProjectionDTO(Long id, String date, String time, Double price, Long audId, Integer audNum) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.price = price;
		this.audId = audId;
		this.audNum = audNum;
	}





}
