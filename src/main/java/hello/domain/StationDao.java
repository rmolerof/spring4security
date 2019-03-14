package hello.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import hello.businessModel.Dispenser;
import hello.businessModel.ExpenseOrCredit;
import hello.businessModel.Station;
import hello.businessModel.Tank;
import hello.businessModel.TotalDay;

@Document(collection = "stations")
public class StationDao {
	
	public static final String STATION_STATUS_NOT_FOUND = "STATION STATUS NOT FOUND";
	public static final StationDao NOT_FOUND = new StationDao(STATION_STATUS_NOT_FOUND);
	
	@Id 
	private ObjectId id;
	
	private Long stationId;
	private String name;
	private String pumpAttendantNames;
	private String shift;
	private String shiftDate = "";
	@Indexed(unique = true)
	private Date date;
	private Map<String, Tank> tanks;
	private Map<String, Dispenser> dispensers;
	private Double totalCash;
	private List<ExpenseOrCredit> expensesAndCredits;
	private TotalDay totalDay;
	
	public StationDao() {
		super();
	}
	
	public StationDao(String name) {
		this.name = name;
	}
	
	public StationDao(Station original) {
		this.id = null==original.getId() ? new ObjectId(): original.getId();
		this.stationId = new Long(original.getStationId());
	    this.name = new String(original.getName());
	    this.pumpAttendantNames = new String(original.getPumpAttendantNames());
	    this.shift = new String(original.getShift());
	    this.shiftDate = new String(original.getShiftDate());
	    this.date = new Date(original.getDate().getTime());
	    this.tanks = new LinkedHashMap<String, Tank>(original.getTanks());
	    this.dispensers = new HashMap<String, Dispenser>(original.getDispensers());
	    this.totalCash = new Double(original.getTotalCash());
	    this.expensesAndCredits =  new ArrayList<ExpenseOrCredit>(original.getExpensesAndCredits());
	    this.totalDay = new TotalDay(original.getTotalDay());
	}
	
	

	@Override
	public String toString() {
		return "StationDao [id=" + id + ", stationId=" + stationId + ", name=" + name + ", pumpAttendantNames="
				+ pumpAttendantNames + ", shift=" + shift + ", shiftDate=" + shiftDate + ", date=" + date + ", tanks="
				+ tanks + ", dispensers=" + dispensers + ", totalCash=" + totalCash + ", expensesAndCredits="
				+ expensesAndCredits + ", totalDay=" + totalDay + "]";
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public String getPumpAttendantNames() {
		return pumpAttendantNames;
	}

	public void setPumpAttendantNames(String pumpAttendantNames) {
		this.pumpAttendantNames = pumpAttendantNames;
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

	public TotalDay getTotalDay() {
		return totalDay;
	}

	public void setTotalDay(TotalDay totalDay) {
		this.totalDay = totalDay;
	}

	public String getShiftDate() {
		return shiftDate;
	}

	public void setShiftDate(String shiftDate) {
		this.shiftDate = shiftDate;
	}
	
}
