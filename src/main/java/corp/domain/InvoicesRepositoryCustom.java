package corp.domain;

import java.util.Date;
import java.util.List;

public interface InvoicesRepositoryCustom {

	List<InvoiceDao> findInvoicesByAmountCriteriaAndVoidedIncludedFlag(String loadInvoiceAmountCriteria, boolean voidedInvoicesIncluded);
	
	List<InvoiceDao> findAllPendingInvoicesTillDate(Date processPendingInvoicesTillDate);
	
	InvoiceDao findLastSentInvoice(String invoiceType);
	
	InvoiceDao findFirstByInvoiceNumberNotVoided(String invoiceNumber);
	
	InvoiceDao findLastNotVoidedInvoice(String invoiceType);
}
