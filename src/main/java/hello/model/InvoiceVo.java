package hello.model;

import java.util.Date;

public class InvoiceVo {
	private String clientAddress;
	private String rucNumber;
	private String clientName;
	private String truckPlateNumber;
	private Double galsD2;
	private Double galsG90;
	private Double galsG95;
	private Double priceD2;
	private Double priceG90;
	private Double priceG95;
	private Double solesD2;
	private Double solesG90;
	private Double solesG95;
	private Date date;
	private String billType;
	private String saveOrUpdate;
	private Double total;
	private Double subTotal;
	private Double totalIGV;
	
	public String getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	public String getRucNumber() {
		return rucNumber;
	}
	public void setRucNumber(String rucNumber) {
		this.rucNumber = rucNumber;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getTruckPlateNumber() {
		return truckPlateNumber;
	}
	public void setTruckPlateNumber(String truckPlateNumber) {
		this.truckPlateNumber = truckPlateNumber;
	}
	public Double getGalsD2() {
		return galsD2;
	}
	public void setGalsD2(Double galsD2) {
		this.galsD2 = galsD2;
	}
	public Double getGalsG90() {
		return galsG90;
	}
	public void setGalsG90(Double galsG90) {
		this.galsG90 = galsG90;
	}
	public Double getGalsG95() {
		return galsG95;
	}
	public void setGalsG95(Double galsG95) {
		this.galsG95 = galsG95;
	}
	public Double getPriceD2() {
		return priceD2;
	}
	public void setPriceD2(Double priceD2) {
		this.priceD2 = priceD2;
	}
	public Double getPriceG90() {
		return priceG90;
	}
	public void setPriceG90(Double priceG90) {
		this.priceG90 = priceG90;
	}
	public Double getPriceG95() {
		return priceG95;
	}
	public void setPriceG95(Double priceG95) {
		this.priceG95 = priceG95;
	}
	public Double getSolesD2() {
		return solesD2;
	}
	public void setSolesD2(Double solesD2) {
		this.solesD2 = solesD2;
	}
	public Double getSolesG90() {
		return solesG90;
	}
	public void setSolesG90(Double solesG90) {
		this.solesG90 = solesG90;
	}
	public Double getSolesG95() {
		return solesG95;
	}
	public void setSolesG95(Double solesG95) {
		this.solesG95 = solesG95;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getSaveOrUpdate() {
		return saveOrUpdate;
	}
	public void setSaveOrUpdate(String saveOrUpdate) {
		this.saveOrUpdate = saveOrUpdate;
	}
	public Double getTotal() {
		return getSolesD2() + getSolesG90() + getSolesG95();
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getSubTotal() {
		return Math.round(getTotal() / 1.18D * 100) / 100D;
	}
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	public Double getTotalIGV() {
		return getTotal() - getSubTotal();
	}
	public void setTotalIGV(Double totalIGV) {
		this.totalIGV = totalIGV;
	}
	
}
