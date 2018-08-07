package hello.businessModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Station {

	private Long id;
	private String name;
	private String shift;
	private Date date;
	private Map<String, Tank> tanks;
	private Map<String, Dispenser> dispensers;
	
	public Station() {
		super();
	}

	public Station(Station original) {
	    if (null == original.getShift()) {
	    	original.setShift("1");
	    }
		this.id = original.id;
	    this.name = new String(original.name);
	    this.shift = new String(getNextShift(original.shift));
	    this.date = new Date(original.getDate().getTime());
	    this.tanks = new HashMap<String, Tank>(original.getTanks());
	    this.dispensers = new HashMap<String, Dispenser>(original.getDispensers());
	}
	
	
	
	@Override
	public String toString() {
		return "Station [id=" + id + ", \nname=" + name + ", \nshift=" + shift + ", \ndate=" + date + ", \ntanks=" + tanks
				+ ", \ndispensers=" + dispensers + "]";
	}

	private String getNextShift(String shift) {
		
		long shiftNumber = Long.valueOf(shift);
		
		if (shiftNumber == 2)  {
			return "1";
		} else {
			return String.valueOf(shiftNumber + 1);
		}
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
}
