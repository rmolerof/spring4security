package hello.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class GasPricesRepositoryImpl implements GasPricesRepositoryCustom {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public GasPricesDao findLatest() {
		Query query = new Query();
		query.limit(1);
		query.with(new Sort(Direction.DESC, "$natural"));
		
		List<GasPricesDao> latestGasPrices = mongoTemplate.find(query, GasPricesDao.class);
	    
		
		return latestGasPrices.size() == 0 ? null: latestGasPrices.get(0);
	}	
}
