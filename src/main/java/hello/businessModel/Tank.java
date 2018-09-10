package hello.businessModel;

public class Tank {
	public Long tankId;
	public String fuelType;
	public Double gals;
	
	public Tank() {
		super();
	}
	
	public Tank(Long tankId, String fuelType, Double gals) {
		this.tankId = tankId;
		this.fuelType = fuelType;
		this.gals = gals;
	}
	
	@Override
	public String toString() {
		return "Tank [tankId=" + tankId + ", fuelType=" + fuelType + ", gals=" + gals + "]";
	}

	public Long getTankId() {
		return tankId;
	}
	public void setTankId(Long tankId) {
		this.tankId = tankId;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public Double getGals() {
		return gals;
	}
	public void setGals(Double gals) {
		this.gals = gals;
	}
	
}
