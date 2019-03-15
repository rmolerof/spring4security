package corp.model;

import org.hibernate.validator.constraints.NotBlank;

public class SearchDocIdCriteria {

	@NotBlank(message = "Número de doc no puede ser vacío")
	String docId;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

}
