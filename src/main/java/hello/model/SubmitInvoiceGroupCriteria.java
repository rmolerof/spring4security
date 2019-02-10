package hello.model;

import org.hibernate.validator.constraints.NotBlank;

public class SubmitInvoiceGroupCriteria {
	
	@NotBlank(message = "Procesamiento debe ser NORMAL o FORZADO.")
	String processingType;
	
	public static final String NORMAL = "NORMAL";
	public static final String FORCED = "FORCED";

	public String getProcessingType() {
		return processingType;
	}

	public void setProcessingType(String processingType) {
		this.processingType = processingType;
	}
	
}
