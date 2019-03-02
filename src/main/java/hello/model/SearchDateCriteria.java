package hello.model;

import org.hibernate.validator.constraints.NotBlank;

public class SearchDateCriteria {
	
	@NotBlank(message = "Fecha no puede ser nula")
	String dateEnd;
	String dateBeg;
	int backDataCount;
	String date;
	String shift;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getDateBeg() {
		return dateBeg;
	}
	public void setDateBeg(String dateBeg) {
		this.dateBeg = dateBeg;
	}
	public int getBackDataCount() {
		return backDataCount;
	}
	public void setBackDataCount(int backDataCount) {
		this.backDataCount = backDataCount;
	}
	
	
}
