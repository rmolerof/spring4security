package corp.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;

public interface InvoicesRepositoryCustom {

	List<InvoiceDao> findInvoicesByAmountCriteriaAndVoidedIncludedFlag(String loadInvoiceAmountCriteria, boolean voidedInvoicesIncluded);
	
	List<InvoiceDao> findInvoicesByAmountCriteriaAndVoidedIncludedFlagForBonus(String loadInvoiceAmountCriteria, boolean voidedInvoicesIncluded);
	
	List<InvoiceDao> findAllPendingInvoicesTillDate(Date processPendingInvoicesTillDate, Sort sort);
	
	List<InvoiceDao> findAllPendingInvoicesTillDateForBonus(Date processPendingInvoicesTillDate, Sort sort);
	
	List<InvoiceDao> findAllInvoicesWithBonusNumberFromDateTillDate(Date fromDate, Date tillDate, Sort sort);
	
	InvoiceDao findLastSentInvoice(String invoiceType);
	
	InvoiceDao findFirstByInvoiceNumberNotVoided(String invoiceNumber);
	
	InvoiceDao findLastNotVoidedInvoice(String invoiceType);
}
