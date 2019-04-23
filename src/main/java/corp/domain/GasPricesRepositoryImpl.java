package corp.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import corp.services.ApplicationService;

public class GasPricesRepositoryImpl implements GasPricesRepositoryCustom {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public List<GasPricesDao> findLatest(String dateEnd, String dateBeg) {
		Criteria criteria = Criteria.where("pumpAttendantNames").ne(ApplicationService.DISCOUNT_USERNAME);
		
		Query query = new Query(criteria);
		if (dateEnd.equalsIgnoreCase("latest") && dateBeg.equalsIgnoreCase("")) {
			query.limit(1);
		} else if (dateEnd.equalsIgnoreCase("latest") && dateBeg.equalsIgnoreCase("previous")) {
			query.limit(2);
		} else { 
			query.limit(Math.abs(Integer.valueOf(dateBeg)));
		}
		
		query.with(new Sort(Direction.DESC, "date"));
		
		List<GasPricesDao> latestGasPrices = mongoTemplate.find(query, GasPricesDao.class);
	    
		
		return latestGasPrices;
	}
	
	public List<GasPricesDao> findLatestByName(String name) {
		
		Criteria criteria = Criteria.where("pumpAttendantNames").is(ApplicationService.DISCOUNT_USERNAME);
		
		Query query = new Query(criteria);
		query.limit(1);
		query.with(new Sort(Direction.DESC, "date"));
		
		List<GasPricesDao> latestGasPrices = mongoTemplate.find(query, GasPricesDao.class);
	    
		
		return latestGasPrices;
	}
}
