package hello.businessModel;

public class Tank {
	public long id;
	public String fuelType;
	public Double gals;
	
	public Tank(long id, String fuelType, Double gals) {
		super();
		this.id = id;
		this.fuelType = fuelType;
		this.gals = gals;
	}
	
	@Override
	public String toString() {
		return "Tank [id=" + id + ", fuelType=" + fuelType + ", gals=" + gals + "]";
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
