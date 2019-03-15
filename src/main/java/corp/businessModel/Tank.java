package corp.businessModel;

import java.util.Date;

public class Tank {
	public Long tankId;
	public String fuelType;
	public Double gals;
	public Double cost;
	private String pumpAttendantNames;
	private Date date;
	private String saveOrUpdate;
	private String shiftDate;
	public String supplierRUC;
	public String truckDriverName;
	public String truckPlateNumber;
	public boolean delivery;
	
	public Tank() {
		super();
	}
	
	public Tank(Long tankId, String fuelType, Double gals, Double cost) {
		this.tankId = tankId;
		this.fuelType = fuelType;
		this.gals = gals;
		this.cost = cost;
	}
	
	@Override
	public String toString() {
		return "Tank [tankId=" + tankId + ", fuelType=" + fuelType + ", gals=" + gals + ", cost=" + cost + "]";
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
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getPumpAttendantNames() {
		return pumpAttendantNames;
	}

	public void setPumpAttendantNames(String pumpAttendantNames) {
		this.pumpAttendantNames = pumpAttendantNames;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public String getSupplierRUC() {
		return supplierRUC;
	}

	public void setSupplierRUC(String supplierRUC) {
		this.supplierRUC = supplierRUC;
	}

	public String getTruckDriverName() {
		return truckDriverName;
	}

	public void setTruckDriverName(String truckDriverName) {
		this.truckDriverName = truckDriverName;
	}

	public String getTruckPlateNumber() {
		return truckPlateNumber;
	}

	public void setTruckPlateNumber(String truckPlateNumber) {
		this.truckPlateNumber = truckPlateNumber;
	}

	public boolean isDelivery() {
		return delivery;
	}

	public void setDelivery(boolean delivery) {
		this.delivery = delivery;
	}
	
}
