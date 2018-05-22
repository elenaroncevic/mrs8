package app.dto;

import app.model.Movie;
import app.model.Projection;

public class ProjectionDTO {
	
	private Long id;
	private String date;
	private String time;
	private Double price;
	private Integer audId;
	
	public ProjectionDTO(Projection projection) {
		this.id=projection.getId();
		this.date=projection.getDate();
		this.time=projection.getTime();
		this.price=projection.getPrice();
		this.audId=projection.getAuditorium().getNumber();
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

	public Integer getAudId() {
		return audId;
	}

	public void setAudId(Integer audId) {
		this.audId = audId;
	}

	public ProjectionDTO(Long id, String date, String time, Double price, Integer audId) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.price = price;
		this.audId = audId;
	}



}
