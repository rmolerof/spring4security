package hello.domain;

import java.util.List;

public interface InvoicesRepositoryCustom {

	List<InvoiceDao> findLatest(String dateEnd, String dateBeg);
	
	List<InvoiceDao> findAllPending(String dateEnd, String dateBeg);
	
	InvoiceDao findLastSentInvoice(String invoiceType);
}
