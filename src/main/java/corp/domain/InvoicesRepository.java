package corp.domain;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface InvoicesRepository extends MongoRepository<InvoiceDao, Long>, InvoicesRepositoryCustom {
	
	public static String TOTAL_INVOICES_TODAY = "TOTAL_TODAY";
	public static String TOTAL_INVOICES_LAST7DAYS = "TOTAL_LAST7DAYS";
	public static String TOTAL_PENDING_INVOICES = "TOTAL_PENDING";
	public static String TOTAL_INVOICES_MONTH = "TOTAL_MONTH";
	public static String TOTAL_INVOICES_YEAR = "TOTAL_YEAR";
	public static Integer WEEK = 7;
	public static Integer MONTH = 30;
	
    
	InvoiceDao findFirstByInvoiceNumberAndSunatStatus(String invoiceNbr, String sunatStatus);
	
	@Query("{sunatStatus: {$regex: ?0}}")
	List<InvoiceDao> findAllPendingByRegex(String statusSunat, Sort sort);
	
	@Query("{bonusStatus: {$regex: ?0}}")
	List<InvoiceDao> findAllPendingByRegexForBonus(String bonusSunat, Sort sort);
	
	@Query("{bonusNumber: {$regex: ?0}}")
	List<InvoiceDao> findInvoicesByBonusNumber(String bonusNumber);
}
