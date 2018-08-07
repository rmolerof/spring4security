package hello.businessModel;

public class TotalDayUnit {
	
	private String name;
	private double totalSolesRevenueDay;
	private double totalProfitDay;
	private double totalGalsSoldDay;
	private double stockGals;
	
	
	public TotalDayUnit(String name) {
		super();
		this.name = name;
		this.totalSolesRevenueDay = 0D;
		this.totalProfitDay = 0D;
		this.totalGalsSoldDay = 0D;
		this.stockGals = 0D;
	}
	
	@Override
	public String toString() {
		return "\nTotalDayUnit [name=" + name + ",\n totalSolesRevenueDay=" + totalSolesRevenueDay + ",\n totalProfitDay="
				+ totalProfitDay + ",\n totalGalsSoldDay=" + totalGalsSoldDay + ",\n stockGals=" + stockGals + "]";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public double getTotalGalsSoldDay() {
		return totalGalsSoldDay;
	}
	public void setTotalGalsSoldDay(double totalGalsSoldDay) {
		this.totalGalsSoldDay = totalGalsSoldDay;
	}
	public double getStockGals() {
		return stockGals;
	}
	public void setStockGals(double stockGals) {
		this.stockGals = stockGals;
	}
	
}
