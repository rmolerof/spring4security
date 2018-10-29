package hello.model;

import org.hibernate.validator.constraints.NotBlank;

public class SearchInvoiceCriteria {

	@NotBlank(message = "Número de recibo no puede ser vacío")
	String invoiceNumber;

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
}
