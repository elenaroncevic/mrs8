package app.dto;

import app.model.Auditorium;

public class AuditoriumDTO {
	private Long id;
	private Integer number;
	private Integer rowNumber;
	
	public AuditoriumDTO() {
		
	}
	
	public AuditoriumDTO(Auditorium aud) {
		this.id=aud.getId();
		this.number=aud.getNumber();
		this.rowNumber=aud.getRows().size();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public AuditoriumDTO(Long id, Integer number, Integer rowNumber) {
		super();
		this.id = id;
		this.number = number;
		this.rowNumber = rowNumber;
	}
	
	
}
