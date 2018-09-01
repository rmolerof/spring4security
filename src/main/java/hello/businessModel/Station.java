package hello.businessModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hello.domain.StationDao;

public class Station {

	private Long stationId;
	private String name;
	private String pumpAttendantNames;
	private String shift;
	private Date date;
	private Map<String, Tank> tanks;
	private Map<String, Dispenser> dispensers;
	private Double totalCash;
	private List<ExpenseOrCredit> expensesAndCredits;
	
	public Station() {
		super();
	}

	public Station(Station original) {
	    if (null == original.getShift()) {
	    	original.setShift("1");
	    }
		this.stationId = original.stationId;
	    this.name = new String(original.name);
	    this.pumpAttendantNames = new String(original.pumpAttendantNames);
	    this.shift = new String(original.shift);
	    this.date = new Date(original.getDate().getTime());
	    this.tanks = new HashMap<String, Tank>(original.getTanks());
	    this.dispensers = new HashMap<String, Dispenser>(original.getDispensers());
	    this.totalCash = new Double(original.getTotalCash());
	    this.expensesAndCredits =  new ArrayList<ExpenseOrCredit>(original.getExpensesAndCredits());
	}
	
	public Station(StationDao stationDao) {
		this.stationId = new Long(stationDao.getStationId());
	    this.name = new String(stationDao.getName());
	    this.pumpAttendantNames = new String(stationDao.getPumpAttendantNames());
	    this.shift = new String(stationDao.getShift());
	    this.date = new Date(stationDao.getDate().getTime());
	    this.tanks = new HashMap<String, Tank>(stationDao.getTanks());
	    this.dispensers = orderDispensers(stationDao.getDispensers());
	    this.totalCash = new Double(stationDao.getTotalCash());
	    this.expensesAndCredits =  new ArrayList<ExpenseOrCredit>(stationDao.getExpensesAndCredits());
	}
	
	public Map<String, Dispenser> orderDispensers(Map<String, Dispenser> dispensers) {
		
		Map<String, Dispenser> orderedDispensers = new LinkedHashMap<String, Dispenser>();
		orderedDispensers.put("d2_1", dispensers.get("d2_1"));
		orderedDispensers.put("g90_1", dispensers.get("g90_1"));
		orderedDispensers.put("d2_2", dispensers.get("d2_2"));
		orderedDispensers.put("d2_3", dispensers.get("d2_3"));
		orderedDispensers.put("g90_2", dispensers.get("g90_2"));
		orderedDispensers.put("d2_4", dispensers.get("d2_4"));
		
		orderedDispensers.put("g95_1", dispensers.get("g95_1"));
		orderedDispensers.put("g90_3", dispensers.get("g90_3"));
		orderedDispensers.put("d2_5", dispensers.get("d2_5"));
		orderedDispensers.put("g95_2", dispensers.get("g95_2"));
		orderedDispensers.put("g90_4", dispensers.get("g90_4"));
		orderedDispensers.put("d2_6", dispensers.get("d2_6"));
		
		return orderedDispensers;
	}
	
	@Override
	public String toString() {
		return "Station [id=" + stationId + ", \nname=" + name + ", \npumpAttendantNames=" + pumpAttendantNames + ", \nshift=" + shift + ", \ndate=" + date + ", \ntanks=" + tanks
				+ ", \ndispensers=" + dispensers + "]";
	}


	public Long getStationId() {
		return stationId;
	}
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Tank> getTanks() {
		return tanks;
	}
	public void setTanks(Map<String, Tank> tanks) {
		this.tanks = tanks;
	}
	public Map<String, Dispenser> getDispensers() {
		return dispensers;
	}
	public void setDispensers(Map<String, Dispenser> dispensers) {
		this.dispensers = dispensers;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	
}
