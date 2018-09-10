package hello.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class TanksRepositoryImpl implements TanksRepositoryCustom {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public TanksDao findLatest() {
		Query query = new Query();
		query.limit(1);
		query.with(new Sort(Direction.DESC, "$natural"));
		
		List<TanksDao> latestTankStatuses = mongoTemplate.find(query, TanksDao.class);
	    
		
		return latestTankStatuses.get(0);
	}	
}
