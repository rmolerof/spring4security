package corp.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import corp.businessModel.Dispenser;
import corp.businessModel.ExpenseOrCredit;
import corp.domain.StationDao;

public class DayDataCriteria {
	private String pumpAttendantNames;
	private Date date;
	private String shift;
	private String shiftDate;
	private Map<String, Double> dayData;
	private Double totalCash;
	private List<ExpenseOrCredit> expensesAndCredits;
	private String saveOrUpdate;
	
	public DayDataCriteria() {
		
	}
	
	public DayDataCriteria(StationDao stationDao) {
		super();
		
		this.pumpAttendantNames = stationDao.getPumpAttendantNames();
		this.date = stationDao.getDate();
		this.shift = stationDao.getShift();
		this.shiftDate = stationDao.getShiftDate();
		
		Map<String, Double> dayData = new LinkedHashMap<String, Double>();
		for (Map.Entry<String, Dispenser> entry: stationDao.getDispensers().entrySet()) {
			dayData.put(entry.getKey(), entry.getValue().getGallons());
		}
		this.dayData = dayData;
		
		this.totalCash = stationDao.getTotalCash();
		this.expensesAndCredits = stationDao.getExpensesAndCredits();
	}
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
