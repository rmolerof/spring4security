package corp.domain;

import java.util.List;

public interface InvoicesRepositoryCustom {

	List<InvoiceDao> findLatest(String dateEnd, String dateBeg);
	
	InvoiceDao findLastSentInvoice(String invoiceType);
	
	InvoiceDao findFirstByInvoiceNumberNotVoided(String invoiceNumber);
	
	InvoiceDao findLastNotVoidedInvoice(String invoiceType);
}
