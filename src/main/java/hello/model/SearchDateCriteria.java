package hello.model;

import org.hibernate.validator.constraints.NotBlank;

public class SearchDateCriteria {
	
	@NotBlank(message = "Fecha no puede ser nula")
	String dateEnd;
	String dateBeg;
	
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
	
}
