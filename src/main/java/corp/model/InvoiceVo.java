package corp.model;

import java.util.Date;
import java.util.HashMap;

import org.bson.types.ObjectId;

import corp.domain.InvoiceDao;

public class InvoiceVo {
	
	public static final String INVOICE_NOT_FOUND_NAME = "CUSTOMER NOT FOUND";
	public static final InvoiceVo NOT_FOUND = new InvoiceVo("0000-00000000", INVOICE_NOT_FOUND_NAME);
	
	private ObjectId id;
	private User user;
	private String invoiceNumber;
	// merchant
	private String myRuc;
	private String companyName;
	private String companyAddress;
	private String companyState;
	private String companyProvince;
	private String companyDistrict;
	private String companyCountryCode;
	private String companyUbigeo;

	// customer
	private String clientDocNumber;
	private String clientName;
	private String clientDocType;
	private String clientAddress;
	private String clientEmailAddress;
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
	// Payment
	private Double subTotal;
	private Double discount;
	private Double totalIGV;
	private Double total;
	private Double electronicPmt;
	private Double cashPmt;
	private Double cashGiven;
	private Double change;
	
	private String totalVerbiage;
	private String invoiceHash;
	private String saveOrUpdate;
	private String status;
	private String sunatErrorStr = "";
	
	private String invoiceTypeModified = "";
	private String invoiceNumberModified  = "";
	private String motiveCd = "";
	private String motiveCdDescription = "";
	private Date dateOfInvoiceModified = new Date(0L);
	private Double igvModified = 0D;
	private Double totalModified = 0D;
	private String bonusNumber = "";
	private String bonusStatus = "";
	private String bonusAccumulatedPoints = "";
	private String sunatStatus = "";
	private boolean sunatValidated = false;
	private Date sunatSubmittedDate = new Date(0L);
	private Date bonusSubmittedDate = new Date(0L);
	
	public InvoiceVo() {
		super();
	}
	
	public InvoiceVo(String invoiceNumber, String clientName) {
		this.invoiceNumber = invoiceNumber;
		this.clientName = clientName;
	}
	
	public InvoiceVo(InvoiceDao invoiceDao) {
		this.id = invoiceDao.getId();
		this.user = new User(null != invoiceDao.getUser() ? invoiceDao.getUser().getName(): "", null != invoiceDao.getUser() ? invoiceDao.getUser().getRoles(): new HashMap<String, Boolean>());
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
		this.discount = new Double(invoiceDao.getDiscount());
		this.electronicPmt = new Double(invoiceDao.getElectronicPmt());
		this.cashPmt = new Double(invoiceDao.getCashPmt());
		this.cashGiven = new Double(invoiceDao.getCashGiven());
		this.change = new Double(invoiceDao.getChange());
		this.totalVerbiage = new String(invoiceDao.getTotalVerbiage());
		this.invoiceHash = new String(invoiceDao.getInvoiceHash());
		this.saveOrUpdate = new String(invoiceDao.getSaveOrUpdate());
		this.invoiceTypeModified = new String(invoiceDao.getInvoiceTypeModified());
		this.invoiceNumberModified  = new String(invoiceDao.getInvoiceNumberModified());
		this.motiveCd = new String(invoiceDao.getMotiveCd());
		this.motiveCdDescription = new String(invoiceDao.getMotiveCdDescription());
		this.dateOfInvoiceModified = new Date(invoiceDao.getDateOfInvoiceModified().getTime());
		this.igvModified = new Double(invoiceDao.getIgvModified());
		this.totalModified = new Double(invoiceDao.getTotalModified());
		this.bonusNumber = new String(invoiceDao.getBonusNumber());
		this.sunatStatus = new String(invoiceDao.getSunatStatus());
		this.bonusStatus = new String(invoiceDao.getBonusStatus());
		this.bonusAccumulatedPoints = new String(invoiceDao.getBonusAccumulatedPoints());
		this.sunatValidated = new Boolean(invoiceDao.isSunatValidated());
		this.clientEmailAddress = new String(null != invoiceDao.getClientEmailAddress() ? invoiceDao.getClientEmailAddress(): "");
		this.sunatSubmittedDate = new Date(invoiceDao.getSunatSubmittedDate().getTime());
		this.bonusSubmittedDate = new Date(invoiceDao.getBonusSubmittedDate().getTime());
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
	public void setTotal(Double total) {
		this.total = total;
	}
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
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

	public String getInvoiceTypeModified() {
		return invoiceTypeModified;
	}

	public void setInvoiceTypeModified(String invoiceTypeModified) {
		this.invoiceTypeModified = invoiceTypeModified;
	}

	public String getInvoiceNumberModified() {
		return invoiceNumberModified;
	}

	public void setInvoiceNumberModified(String invoiceNumberModified) {
		this.invoiceNumberModified = invoiceNumberModified;
	}

	public String getMotiveCd() {
		return motiveCd;
	}

	public void setMotiveCd(String motiveCd) {
		this.motiveCd = motiveCd;
	}

	public String getMotiveCdDescription() {
		return motiveCdDescription;
	}

	public void setMotiveCdDescription(String motiveCdDescription) {
		this.motiveCdDescription = motiveCdDescription;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getElectronicPmt() {
		return electronicPmt;
	}

	public void setElectronicPmt(Double electronicPmt) {
		this.electronicPmt = electronicPmt;
	}

	public Double getChange() {
		return change;
	}

	public void setChange(Double change) {
		this.change = change;
	}

	public Double getCashPmt() {
		return cashPmt;
	}

	public void setCashPmt(Double cashPmt) {
		this.cashPmt = cashPmt;
	}

	public Double getCashGiven() {
		return cashGiven;
	}

	public void setCashGiven(Double cashGiven) {
		this.cashGiven = cashGiven;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public Double getTotalIGV() {
		return totalIGV;
	}

	public Double getTotal() {
		return total;
	}

	public Date getDateOfInvoiceModified() {
		return dateOfInvoiceModified;
	}

	public void setDateOfInvoiceModified(Date dateOfInvoiceModified) {
		this.dateOfInvoiceModified = dateOfInvoiceModified;
	}

	public Double getIgvModified() {
		return igvModified;
	}

	public void setIgvModified(Double igvModified) {
		this.igvModified = igvModified;
	}

	public Double getTotalModified() {
		return totalModified;
	}

	public void setTotalModified(Double totalModified) {
		this.totalModified = totalModified;
	}

	public String getBonusNumber() {
		return bonusNumber;
	}

	public void setBonusNumber(String bonusNumber) {
		this.bonusNumber = bonusNumber;
	}

	public String getSunatStatus() {
		return sunatStatus;
	}

	public void setSunatStatus(String sunatStatus) {
		this.sunatStatus = sunatStatus;
	}

	public boolean isSunatValidated() {
		return sunatValidated;
	}

	public void setSunatValidated(boolean sunatValidated) {
		this.sunatValidated = sunatValidated;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getClientEmailAddress() {
		return clientEmailAddress;
	}

	public void setClientEmailAddress(String clientEmailAddress) {
		this.clientEmailAddress = clientEmailAddress;
	}

	public String getBonusStatus() {
		return bonusStatus;
	}

	public void setBonusStatus(String bonusStatus) {
		this.bonusStatus = bonusStatus;
	}

	public String getBonusAccumulatedPoints() {
		return bonusAccumulatedPoints;
	}

	public void setBonusAccumulatedPoints(String bonusAccumulatedPoints) {
		this.bonusAccumulatedPoints = bonusAccumulatedPoints;
	}

	public Date getSunatSubmittedDate() {
		return sunatSubmittedDate;
	}

	public void setSunatSubmittedDate(Date sunatSubmittedDate) {
		this.sunatSubmittedDate = sunatSubmittedDate;
	}

	public Date getBonusSubmittedDate() {
		return bonusSubmittedDate;
	}

	public void setBonusSubmittedDate(Date bonusSubmittedDate) {
		this.bonusSubmittedDate = bonusSubmittedDate;
	}

	public String getMyRuc() {
		return myRuc;
	}

	public void setMyRuc(String myRuc) {
		this.myRuc = myRuc;
	}
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyState() {
		return companyState;
	}

	public void setCompanyState(String companyState) {
		this.companyState = companyState;
	}

	public String getCompanyProvince() {
		return companyProvince;
	}

	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}

	public String getCompanyDistrict() {
		return companyDistrict;
	}

	public void setCompanyDistrict(String companyDistrict) {
		this.companyDistrict = companyDistrict;
	}

	public String getCompanyCountryCode() {
		return companyCountryCode;
	}

	public void setCompanyCountryCode(String companyCountryCode) {
		this.companyCountryCode = companyCountryCode;
	}

	public String getCompanyUbigeo() {
		return companyUbigeo;
	}

	public void setCompanyUbigeo(String companyUbigeo) {
		this.companyUbigeo = companyUbigeo;
	}

}
