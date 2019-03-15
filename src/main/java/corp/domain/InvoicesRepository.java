package corp.domain;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface InvoicesRepository extends MongoRepository<InvoiceDao, Long>, InvoicesRepositoryCustom {
	
	InvoiceDao findFirstByInvoiceNumberAndSunatStatus(String invoiceNbr, String sunatStatus);
	
	@Query("{sunatStatus: {$regex: ?0}}")
	List<InvoiceDao> findAllPendingByRegex(String statusSunat, Sort sort);
	
}
