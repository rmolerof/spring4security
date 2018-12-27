package hello.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import hello.businessModel.ExpenseOrCredit;

public class DayDataCriteria {
	private String pumpAttendantNames;
	private Date date;
	private String shift;
	private String shiftDate;
	private Map<String, Double> dayData;
	private Double totalCash;
	private List<ExpenseOrCredit> expensesAndCredits;
	private String saveOrUpdate;
	
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
	public Double getTotalCash() {
		return totalCash;
	}
	public void setTotalCash(Double totalCash) {
		this.totalCash = totalCash;
	}
	public List<ExpenseOrCredit> getExpensesAndCredits() {
		return expensesAndCredits;
	}
	public void setExpensesAndCredits(List<ExpenseOrCredit> expensesAndCredits) {
		this.expensesAndCredits = expensesAndCredits;
	}
	public String getSaveOrUpdate() {
		return saveOrUpdate;
	}
	public void setSaveOrUpdate(String saveOrUpdate) {
		this.saveOrUpdate = saveOrUpdate;
	}
	public String getShiftDate() {
		return shiftDate;
	}
	public void setShiftDate(String shiftDate) {
		this.shiftDate = shiftDate;
	}
	
}
