package corp.model;

import java.util.Date;

//import org.hibernate.validator.constraints.NotBlank;

public class SubmitInvoiceGroupCriteria {
	
//	@NotBlank(message = "Procesamiento debe ser NORMAL o FORZADO.")
	String processingType;
	Date processPendingInvoicesTillDate;
	String invoiceNumber;
	boolean bonusControlsEnabled;
	
	public static final String NORMAL = "NORMAL";
	public static final String FORCED = "FORCED";

	public String getProcessingType() {
		return processingType;
	}

	public void setProcessingType(String processingType) {
		this.processingType = processingType;
	}

	public Date getProcessPendingInvoicesTillDate() {
		return processPendingInvoicesTillDate;
	}

	public void setProcessPendingInvoicesTillDate(Date processPendingInvoicesTillDate) {
		this.processPendingInvoicesTillDate = processPendingInvoicesTillDate;
	}

	public boolean isBonusControlsEnabled() {
		return bonusControlsEnabled;
	}

	public void setBonusControlsEnabled(boolean bonusControlsEnabled) {
		this.bonusControlsEnabled = bonusControlsEnabled;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	
}
