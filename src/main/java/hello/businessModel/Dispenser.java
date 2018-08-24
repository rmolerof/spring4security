package hello.businessModel;

public class Dispenser {
	public long id;
	public String name;
	public double gallons;
	public double price;
	public double cost;
	public long pattern;
	
	public Dispenser(long id, String name, double gallons, double price, double cost, long pattern) {
		super();
		this.id = id;
		this.name = name;
		this.gallons = gallons;
		this.price = price;
		this.cost = cost;
		this.pattern = pattern;
	}

	@Override
	public String toString() {
		return "Dispenser [id=" + id + ", name=" + name + ", gallons=" + gallons + ", price="
				+ price + ", cost=" + cost + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getGallons() {
		return gallons;
	}

	public void setGallons(double gallons) {
		this.gallons = gallons;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public long getPattern() {
		return pattern;
	}

	public void setPattern(long pattern) {
		this.pattern = pattern;
	}
		
}
