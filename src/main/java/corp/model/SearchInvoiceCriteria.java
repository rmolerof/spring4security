package corp.model;

import org.hibernate.validator.constraints.NotBlank;

public class SearchInvoiceCriteria {

	@NotBlank(message = "Número de recibo no puede ser vacío")
	String invoiceNumber;
	String selectedOption;
	String clientEmailAddress;
	String clientDocNumber;
	String clientDocType;

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

	public String getClientEmailAddress() {
		return clientEmailAddress;
	}

	public void setClientEmailAddress(String clientEmailAddress) {
		this.clientEmailAddress = clientEmailAddress;
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
	
}
