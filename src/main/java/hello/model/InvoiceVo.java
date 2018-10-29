package hello.model;

import java.util.Date;

import org.bson.types.ObjectId;

import hello.domain.InvoiceDao;

public class InvoiceVo {
	private ObjectId id;
	private String invoiceNumber;
	// customer
	private String clientDocNumber;
	private String clientName;
	private String clientDocType;
	private String clientAddress;
	private String truckPlateNumber;
	// invoice breakdown
	private Date date;
	private String invoiceType;
	// Break-down
	private Double galsD2 = 0D;
	private Double galsG90 = 0D;
	private Double galsG95 = 0D;
	private Double priceD2 = 0D;
	private Double priceG90 = 0D;
	private Double priceG95 = 0D;
	private Double solesD2 = 0D;
	private Double solesG90 = 0D;
	private Double solesG95 = 0D;
	// Totals
	private Double total;
	private Double subTotal;
	private Double totalIGV;
	
	private String totalVerbiage;
	private String invoiceHash;
	private String saveOrUpdate;
	private String status;
	private String sunatErrorStr;
	
	public InvoiceVo() {
		super();
	}
	
	public InvoiceVo(InvoiceDao invoiceDao) {
		this.id = invoiceDao.getId();
		this.invoiceNumber = new String(invoiceDao.getInvoiceNumber());
		this.clientDocNumber = new String(invoiceDao.getClientDocNumber());
		this.clientName = new String(invoiceDao.getClientName());
		this.clientDocType = new String(invoiceDao.getClientDocType());
		this.clientAddress = new String(invoiceDao.getClientAddress());
		this.truckPlateNumber = new String(invoiceDao.getTruckPlateNumber());
		this.date = new Date(invoiceDao.getDate().getTime());
		this.invoiceType = new String(invoiceDao.getInvoiceType());
		this.galsD2 = new Double(invoiceDao.getGalsD2());
		this.galsG90 = new Double(invoiceDao.getGalsG90());
		this.galsG95 = new Double(invoiceDao.getGalsG95());
		this.priceD2 = new Double(invoiceDao.getPriceD2());
		this.priceG90 = new Double(invoiceDao.getPriceG90());
		this.priceG95 = new Double(invoiceDao.getPriceG95());
		this.solesD2 = new Double(invoiceDao.getSolesD2());
		this.solesG90 = new Double(invoiceDao.getSolesG90());
		this.solesG95 = new Double(invoiceDao.getSolesG95());
		this.subTotal = new Double(invoiceDao.getSubTotal());
		this.totalIGV = new Double(invoiceDao.getTotalIGV());
		this.total = new Double(invoiceDao.getTotal());
		this.totalVerbiage = new String(invoiceDao.getTotalVerbiage());
		this.invoiceHash = new String(invoiceDao.getInvoiceHash());
		this.saveOrUpdate = new String(invoiceDao.getSaveOrUpdate());
		
	}
	
	public static double roundTwo(double amt) {
		return Math.round(amt * 100.0) / 100.0;
	}
	public String getInvoiceHash() {
		return invoiceHash;
	}
	public void setInvoiceHash(String invoiceHash) {
		this.invoiceHash = invoiceHash;
	}
	public String getClientDocNumber() {
		return clientDocNumber;
	}
	public void setClientDocNumber(String clientDocNumber) {
		this.clientDocNumber = clientDocNumber;
	}
	public String getClientDocType() {
		return clientDocType;
	}
	public void setClientDocType(String clientDocType) {
		this.clientDocType = clientDocType;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
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
	public String getSaveOrUpdate() {
		return saveOrUpdate;
	}
	public void setSaveOrUpdate(String saveOrUpdate) {
		this.saveOrUpdate = saveOrUpdate;
	}
	public Double getTotal() {
		return roundTwo(getSolesD2() + getSolesG90() + getSolesG95());
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getSubTotal() {
		return roundTwo(getTotal() /  1.18D);
	}
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	public Double getTotalIGV() {
		return roundTwo(getTotal() - getSubTotal());
	}
	public void setTotalIGV(Double totalIGV) {
		this.totalIGV = totalIGV;
	}
	public String getTotalVerbiage() {
		return totalVerbiage;
	}
	public void setTotalVerbiage(String totalVerbiage) {
		this.totalVerbiage = totalVerbiage;
	}
	public String getSunatErrorStr() {
		return sunatErrorStr;
	}
	public void setSunatErrorStr(String sunatErrorStr) {
		this.sunatErrorStr = sunatErrorStr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
}
