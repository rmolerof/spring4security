package hello.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class StationRepositoryImpl implements StationRepositoryCustom {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public StationDao findLatest() {
		Query query = new Query();
		query.limit(1);
		query.with(new Sort(Direction.DESC, "$natural"));
		
		List<StationDao> latestStationStatuses = mongoTemplate.find(query, StationDao.class);
	    
		
		return latestStationStatuses.size() == 0 ? null: latestStationStatuses.get(0);
	}

	@Override
	public List<StationDao> findLatestMonth() {
		Query query = new Query();
		query.with(new Sort(Direction.DESC, "$natural"));
		
		List<StationDao> latestStationStatuses = mongoTemplate.find(query, StationDao.class);
	    
		
		return latestStationStatuses;
	}	
}
