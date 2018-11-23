package hello.model;

import org.hibernate.validator.constraints.NotBlank;

public class SearchInvoiceCriteria {

	@NotBlank(message = "Número de recibo no puede ser vacío")
	String invoiceNumber;
	String selectedOption;

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
	
}
