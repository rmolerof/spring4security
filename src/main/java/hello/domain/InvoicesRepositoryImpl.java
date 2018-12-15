package hello.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class InvoicesRepositoryImpl implements InvoicesRepositoryCustom {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
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

}
