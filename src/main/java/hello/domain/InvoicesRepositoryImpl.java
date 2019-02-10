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
	
	public List<InvoiceDao> findAllPending(String dateEnd, String dateBeg) {
		
		Query query = new Query(Criteria.where("sunatStatus").is("PENDIENTE"));
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
		
		if (invoiceType.equals(UserService.BOLETA)) {
			query = new Query(Criteria.where("sunatStatus").is("ENVIADO").and("invoiceType").is(invoiceType));
		} else {
			query = new Query(Criteria.where("sunatStatus").is("ENVIADO").and("invoiceType").ne(UserService.BOLETA));
		}
		
		query.limit(1);
		query.with(new Sort(Direction.DESC, "date"));
		
		List<InvoiceDao> invoiceDaos = mongoTemplate.find(query, InvoiceDao.class);
		
		return invoiceDaos.size() > 0 ? invoiceDaos.get(0): InvoiceDao.NOT_FOUND;
	}

}
