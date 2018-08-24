package hello.model;

import java.util.Date;
import java.util.Map;

public class DayDataCriteria {
	private String pumpAttendantNames;
	private Date date;
	private String shift;
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
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getPumpAttendantNames() {
		return pumpAttendantNames;
	}
	public void setPumpAttendantNames(String pumpAttendantNames) {
		this.pumpAttendantNames = pumpAttendantNames;
	}
	
}
