package app.dto;
import app.model.Row;
import app.model.Seat;

public class SeatDTO {
	
	private Long id;
	private Integer number;
	private Boolean active;
	private Long rowNum;
	
	public SeatDTO() {
		
	}

	public SeatDTO(Long id, Integer number, Boolean active, Long rowNum) {
		super();
		this.id = id;
		this.number = number;
		this.active = active;
		this.rowNum = rowNum;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getRowNum() {
		return rowNum;
	}

	public void setRowNum(Long rowNum) {
		this.rowNum = rowNum;
	}
	
	public SeatDTO(Seat seat) {
		this.id=seat.getId();
		this.number=seat.getNumber();
		this.rowNum=seat.getRow().getNumber();
		this.active=seat.getActive();
	}
	
}
