package hello.model;

import java.util.Date;
import java.util.Map;

public class DayDataCriteria {
	
	private Date date;
	private Map<String, Double> dayData;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Map<String, Double> getDayData() {
		return dayData;
	}
	public void setDayData(Map<String, Double> dayData) {
		this.dayData = dayData;
	}
	
}
