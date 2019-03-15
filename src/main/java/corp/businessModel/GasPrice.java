package corp.businessModel;

public class GasPrice {
	public String fuelType;
	public Double cost;
	public Double price;
	
	public GasPrice() {
		super();
	}
	
	public GasPrice(String fuelType, Double cost, Double price) {
		this.fuelType = fuelType;
		this.cost = cost;
		this.price = price;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
