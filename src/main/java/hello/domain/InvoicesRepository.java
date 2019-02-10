package hello.domain;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface InvoicesRepository extends MongoRepository<InvoiceDao, Long>, InvoicesRepositoryCustom {
	
	InvoiceDao findFirstByInvoiceNumber(String invoiceNbr);
	
	@Query("{sunatStatus: {$regex: ?0}}")
	List<InvoiceDao> findAllPendingByRegex(String statusSunat, Sort sort);
	
}
