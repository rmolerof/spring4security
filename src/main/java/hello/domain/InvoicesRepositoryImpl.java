package hello.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import hello.services.UserService;

public class InvoicesRepositoryImpl implements InvoicesRepositoryCustom {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public List<InvoiceDao> findLatest(String dateEnd, String dateBeg) {
		Query query = new Query();
		if (dateEnd.equalsIgnoreCase("latest") && dateBeg.equalsIgnoreCase("")) {
			query.limit(1);
		} else if (dateEnd.equalsIgnoreCase("latest") && dateBeg.equalsIgnoreCase("previous")) {
			query.limit(2);
		} else { 
			query.limit(Math.abs(Integer.valueOf(dateBeg)));
		}
		
		query.with(new Sort(Direction.DESC, "date"));
		
		List<InvoiceDao> invoiceDaos = mongoTemplate.find(query, InvoiceDao.class);
	    
		
		return invoiceDaos;
	}
	
	public InvoiceDao findLastSentInvoice(String invoiceType) {
		
		Query query = null;
		Criteria invoiceTypeCriteria = null;
		Criteria notaDeCreditoOfInvoiceTypeCriteria = null;
		
		if (invoiceType.equals(UserService.BOLETA)) {
			invoiceTypeCriteria = Criteria.where("sunatStatus").is(UserService.SUNAT_SENT_STATUS).and("invoiceType").is(UserService.BOLETA);
			notaDeCreditoOfInvoiceTypeCriteria = Criteria.where("sunatStatus").is(UserService.SUNAT_SENT_STATUS).and("invoiceTypeModified").is(UserService.BOLETA);
			query = new Query(new Criteria().orOperator(invoiceTypeCriteria, notaDeCreditoOfInvoiceTypeCriteria));
		} else {
			invoiceTypeCriteria = Criteria.where("sunatStatus").is(UserService.SUNAT_SENT_STATUS).and("invoiceType").is(UserService.FACTURA);
			notaDeCreditoOfInvoiceTypeCriteria = Criteria.where("sunatStatus").is(UserService.SUNAT_SENT_STATUS).and("invoiceTypeModified").is(UserService.FACTURA);
			query = new Query(new Criteria().orOperator(invoiceTypeCriteria, notaDeCreditoOfInvoiceTypeCriteria));
		}
		
		query.limit(1);
		query.with(new Sort(Direction.DESC, "date"));
		
		List<InvoiceDao> invoiceDaos = mongoTemplate.find(query, InvoiceDao.class);
		
		return invoiceDaos.size() > 0 ? invoiceDaos.get(0): InvoiceDao.NOT_FOUND;
	}
	
	public InvoiceDao findLastNotVoidedInvoice(String invoiceType) {
		
		Query query = null;
		Criteria invoiceTypeCriteria = null;
		Criteria notaDeCreditoOfInvoiceTypeCriteria = null;
		
		if (invoiceType.equals(UserService.BOLETA)) {
			invoiceTypeCriteria = Criteria.where("sunatStatus").ne(UserService.SUNAT_VOIDED_STATUS).and("invoiceType").is(UserService.BOLETA);
			notaDeCreditoOfInvoiceTypeCriteria = Criteria.where("sunatStatus").ne(UserService.SUNAT_VOIDED_STATUS).and("invoiceTypeModified").is(UserService.BOLETA);
			query = new Query(new Criteria().orOperator(invoiceTypeCriteria, notaDeCreditoOfInvoiceTypeCriteria));
		} else {
			invoiceTypeCriteria = Criteria.where("sunatStatus").ne(UserService.SUNAT_VOIDED_STATUS).and("invoiceType").is(UserService.FACTURA);
			notaDeCreditoOfInvoiceTypeCriteria = Criteria.where("sunatStatus").ne(UserService.SUNAT_VOIDED_STATUS).and("invoiceTypeModified").is(UserService.FACTURA);
			query = new Query(new Criteria().orOperator(invoiceTypeCriteria, notaDeCreditoOfInvoiceTypeCriteria));
		}
		
		query.limit(1);
		query.with(new Sort(Direction.DESC, "date"));
		
		List<InvoiceDao> invoiceDaos = mongoTemplate.find(query, InvoiceDao.class);
		
		return invoiceDaos.size() > 0 ? invoiceDaos.get(0): InvoiceDao.NOT_FOUND;
	}

	@Override
	public InvoiceDao findFirstByInvoiceNumberNotVoided(String invoiceNumber) {
		
		Query query = new Query(Criteria.where("sunatStatus").ne(UserService.SUNAT_VOIDED_STATUS).and("invoiceNumber").is(invoiceNumber));
		query.limit(1);
		query.with(new Sort(Direction.DESC, "date"));
		
		List<InvoiceDao> invoiceDaos = mongoTemplate.find(query, InvoiceDao.class);
		return invoiceDaos.size() > 0 ? invoiceDaos.get(0): InvoiceDao.NOT_FOUND;
	}

}
