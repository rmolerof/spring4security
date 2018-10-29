package hello.domain;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import hello.model.InvoiceVo;

@Document(collection = "invoices")
public class InvoiceDao {
	
	@Id 
	private ObjectId id;

	private String invoiceNumber;
	// customer
	private String clientDocNumber;
	private String clientName;
	private String clientDocType;
	private String clientAddress;
	private String truckPlateNumber;
	// invoice breakdown
	@Indexed(unique = true)
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
	private String sunatErrorStr;

	public InvoiceDao() {
		super();
	}
	
	public InvoiceDao(InvoiceVo invoiceVo) {
		this.id = new ObjectId();
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
		this.totalVerbiage = new String(invoiceVo.getTotalVerbiage());
		this.invoiceHash = new String(invoiceVo.getInvoiceHash());
		this.saveOrUpdate = new String(invoiceVo.getSaveOrUpdate());
		
	}

	public InvoiceDao(ObjectId id, String invoiceNumber, String clientDocNumber, String clientName,
			String clientDocType, String clientAddress, String truckPlateNumber, Date date, String invoiceType,
			Double galsD2, Double galsG90, Double galsG95, Double priceD2, Double priceG90, Double priceG95,
			Double solesD2, Double solesG90, Double solesG95, Double total, Double subTotal, Double totalIGV,
			String totalVerbiage, String invoiceHash, String saveOrUpdate, String sunatErrorStr) {
		super();
		this.id = id;
		this.invoiceNumber = invoiceNumber;
		this.clientDocNumber = clientDocNumber;
		this.clientName = clientName;
		this.clientDocType = clientDocType;
		this.clientAddress = clientAddress;
		this.truckPlateNumber = truckPlateNumber;
		this.date = date;
		this.invoiceType = invoiceType;
		this.galsD2 = galsD2;
		this.galsG90 = galsG90;
		this.galsG95 = galsG95;
		this.priceD2 = priceD2;
		this.priceG90 = priceG90;
		this.priceG95 = priceG95;
		this.solesD2 = solesD2;
		this.solesG90 = solesG90;
		this.solesG95 = solesG95;
		this.total = total;
		this.subTotal = subTotal;
		this.totalIGV = totalIGV;
		this.totalVerbiage = totalVerbiage;
		this.invoiceHash = invoiceHash;
		this.saveOrUpdate = saveOrUpdate;
		this.sunatErrorStr = sunatErrorStr;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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
	
}
