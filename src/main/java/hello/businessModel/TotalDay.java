package hello.businessModel;

import java.util.HashMap;
import java.util.Map;

public class TotalDay {
	
	private Map<String, TotalDayUnit> totalDayUnits = null;
	private double totalSolesRevenueDay;
	private double totalProfitDay;
	
	public TotalDay() {
		if(null == totalDayUnits) {
			totalDayUnits = new HashMap<String, TotalDayUnit>();
		}
		this.totalSolesRevenueDay = 0D;
		this.totalProfitDay = 0D;
	}
	
	public TotalDay(TotalDay totalDay) {
		this.totalDayUnits = new HashMap<String, TotalDayUnit>(totalDay.getTotalDayUnits());
		this.totalSolesRevenueDay = new Double(totalDay.getTotalSolesRevenueDay());
		this.totalProfitDay = new Double(totalDay.getTotalProfitDay());
	}
	
	@Override
	public String toString() {
		return "TotalDay [totalDayUnits=" + totalDayUnits + ",\n totalSolesRevenueDay=" + totalSolesRevenueDay
				+ ",\n totalProfitDay=" + totalProfitDay + "]";
	}

	public void setTotalGalsSoldDay(String name, double gals) {
		TotalDayUnit unit = getTotalDayUnit(name);
		
		unit.setTotalGalsSoldDay(gals);
	}
	
	public double getTotalGalsSoldDay(String name) {
		TotalDayUnit unit = getTotalDayUnit(name);
		
		return unit.getTotalGalsSoldDay();
	}
	
	public TotalDayUnit getTotalDayUnit(String name) {
		TotalDayUnit unit = getTotalDayUnits().get(name);
		
		if (null == unit) {
			unit = new TotalDayUnit(name);
			getTotalDayUnits().put(name, unit);
		}
		
		return unit;
	}
	
	public void setTotalSolesRevenueDay(String name, double soles) {
		TotalDayUnit unit = getTotalDayUnits().get(name);
		
		unit.setTotalSolesRevenueDay(soles);
	}
	
	public double getTotalSolesRevenueDay(String name) {
		TotalDayUnit unit = getTotalDayUnits().get(name);
		
		return unit.getTotalSolesRevenueDay();
	}
	
	public void setTotalProfitDay(String name, double soles) {
		TotalDayUnit unit = getTotalDayUnits().get(name);
		
		unit.setTotalProfitDay(soles);
	}
	
	public double getTotalProfitDay(String name) {
		TotalDayUnit unit = getTotalDayUnits().get(name);
		
		return unit.getTotalProfitDay();
	}
	
	public void setStockGals(String name, double gals) {
		TotalDayUnit unit = getTotalDayUnits().get(name);
		
		unit.setStockGals(gals);
	}
	
	public double getStockGals(String name) {
		TotalDayUnit unit = getTotalDayUnits().get(name);
		
		return unit.getStockGals();
	}
	
	public Map<String, TotalDayUnit> getTotalDayUnits() {
		return totalDayUnits;
	}

	public void setTotalDayUnits(Map<String, TotalDayUnit> totalDayUnits) {
		this.totalDayUnits = totalDayUnits;
	}

	public double getTotalSolesRevenueDay() {
		return totalSolesRevenueDay;
	}

	public void setTotalSolesRevenueDay(double totalSolesRevenueDay) {
		this.totalSolesRevenueDay = totalSolesRevenueDay;
	}

	public double getTotalProfitDay() {
		return totalProfitDay;
	}

	public void setTotalProfitDay(double totalProfitDay) {
		this.totalProfitDay = totalProfitDay;
	}
	
}
