package corp.domain;

import java.util.Date;
import java.util.HashMap;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import corp.model.InvoiceVo;
import corp.model.User;

@Document(collection = "invoices")
public class InvoiceDao {
	
	public static final String INVOICE_NOT_FOUND_NAME = "CUSTOMER NOT FOUND";
	public static final InvoiceDao NOT_FOUND = new InvoiceDao("0000-00000000", INVOICE_NOT_FOUND_NAME);
	
	@Id 
	private ObjectId id;
	private User user;
	private String username;
	@Indexed(unique = true)
	private String invoiceNumber;
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

	public InvoiceDao() {
		super();
	}
	
	public InvoiceDao(String invoiceNumber, String clientName) {
		this.invoiceNumber = invoiceNumber;
		this.clientName = clientName;
	}
	
	public InvoiceDao(InvoiceVo invoiceVo) {
		if (null == invoiceVo.getId() ) {
			this.id = new ObjectId();
		} else {
			this.id = invoiceVo.getId().getTimestamp() > 0 ? invoiceVo.getId(): new ObjectId();
		}
		this.user = new User(null != invoiceVo.getUser() ? invoiceVo.getUser().getName(): "", null != invoiceVo.getUser() ? invoiceVo.getUser().getRoles(): new HashMap<String, Boolean>());
		this.invoiceNumber = new String(invoiceVo.getInvoiceNumber());
		this.clientDocNumber = new String(invoiceVo.getClientDocNumber());
		this.clientName = new String(invoiceVo.getClientName());
		this.clientDocType = new String(invoiceVo.getClientDocType());
		this.clientAddress = new String(invoiceVo.getClientAddress());
		this.truckPlateNumber = new String(invoiceVo.getTruckPlateNumber());
		this.date = new Date(invoiceVo.getDate().getTime());
		this.invoiceType = new String(invoiceVo.getInvoiceType());
		this.galsD2 = new Double(invoiceVo.getGalsD2());
		this.galsG90 = new Double(invoiceVo.getGalsG90());
		this.galsG95 = new Double(invoiceVo.getGalsG95());
		this.priceD2 = new Double(invoiceVo.getPriceD2());
		this.priceG90 = new Double(invoiceVo.getPriceG90());
		this.priceG95 = new Double(invoiceVo.getPriceG95());
		this.solesD2 = new Double(invoiceVo.getSolesD2());
		this.solesG90 = new Double(invoiceVo.getSolesG90());
		this.solesG95 = new Double(invoiceVo.getSolesG95());
		this.subTotal = new Double(invoiceVo.getSubTotal());
		this.totalIGV = new Double(invoiceVo.getTotalIGV());
		this.total = new Double(invoiceVo.getTotal());
		this.discount = new Double(invoiceVo.getDiscount());
		this.electronicPmt = new Double(invoiceVo.getElectronicPmt());
		this.cashPmt = new Double(invoiceVo.getCashPmt());
		this.cashGiven = new Double(invoiceVo.getCashGiven());
		this.change = new Double(invoiceVo.getChange());
		this.totalVerbiage = new String(invoiceVo.getTotalVerbiage());
		this.invoiceHash = new String(invoiceVo.getInvoiceHash());
		this.saveOrUpdate = new String(invoiceVo.getSaveOrUpdate());
		this.invoiceTypeModified = new String(invoiceVo.getInvoiceTypeModified());
		this.invoiceNumberModified  = new String(invoiceVo.getInvoiceNumberModified());
		this.motiveCd = new String(invoiceVo.getMotiveCd());
		this.motiveCdDescription = new String(invoiceVo.getMotiveCdDescription());
		this.dateOfInvoiceModified = new Date(invoiceVo.getDateOfInvoiceModified().getTime());
		this.igvModified = new Double(invoiceVo.getIgvModified());
		this.totalModified = new Double(invoiceVo.getTotalModified());
		this.bonusNumber = new String(invoiceVo.getBonusNumber());
		this.sunatStatus = new String(invoiceVo.getSunatStatus());
		this.bonusStatus = new String(invoiceVo.getBonusStatus());
		this.bonusAccumulatedPoints = new String(invoiceVo.getBonusAccumulatedPoints());
		this.sunatValidated = new Boolean(invoiceVo.isSunatValidated());
		this.clientEmailAddress = new String(null != invoiceVo.getClientEmailAddress() ? invoiceVo.getClientEmailAddress(): "");
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getUsername() {
		return user.getName();
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getClientDocNumber() {
		return clientDocNumber;
	}

	public void setClientDocNumber(String clientDocNumber) {
		this.clientDocNumber = clientDocNumber;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientDocType() {
		return clientDocType;
	}

	public void setClientDocType(String clientDocType) {
		this.clientDocType = clientDocType;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getTruckPlateNumber() {
		return truckPlateNumber;
	}

	public void setTruckPlateNumber(String truckPlateNumber) {
		this.truckPlateNumber = truckPlateNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
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

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getTotalIGV() {
		return totalIGV;
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

	public String getInvoiceHash() {
		return invoiceHash;
	}

	public void setInvoiceHash(String invoiceHash) {
		this.invoiceHash = invoiceHash;
	}

	public String getSaveOrUpdate() {
		return saveOrUpdate;
	}

	public void setSaveOrUpdate(String saveOrUpdate) {
		this.saveOrUpdate = saveOrUpdate;
	}

	public String getSunatErrorStr() {
		return sunatErrorStr;
	}

	public void setSunatErrorStr(String sunatErrorStr) {
		this.sunatErrorStr = sunatErrorStr;
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

	public Double getChange() {
		return change;
	}

	public void setChange(Double change) {
		this.change = change;
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

}
