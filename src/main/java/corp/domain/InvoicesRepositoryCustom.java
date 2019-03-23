package corp.domain;

import java.util.List;

public interface InvoicesRepositoryCustom {

	List<InvoiceDao> findInvoicesByAmountCriteriaAndVoidedIncludedFlag(String loadInvoiceAmountCriteria, boolean voidedInvoicesIncluded);
	
	InvoiceDao findLastSentInvoice(String invoiceType);
	
	InvoiceDao findFirstByInvoiceNumberNotVoided(String invoiceNumber);
	
	InvoiceDao findLastNotVoidedInvoice(String invoiceType);
}
