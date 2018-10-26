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
	public List<StationDao> findLatest(String dateEnd, String dateBeg) {
		
		Query query = new Query();
		if (dateEnd.equalsIgnoreCase("latest") && dateBeg.equalsIgnoreCase("")) { 
			query.limit(1);
		} else if (dateEnd.equalsIgnoreCase("latest") && dateBeg.equalsIgnoreCase("previous")) {
			query.limit(2);
		}
		//query.with(new Sort(Direction.DESC, "$natural"));
		query.with(new Sort(Direction.DESC, "date"));
		
		List<StationDao> latestStationStatuses = mongoTemplate.find(query, StationDao.class);
	    
		
		return latestStationStatuses.size() == 0 ? null: latestStationStatuses;
	}

	@Override
	public List<StationDao> findLatestMonth() {
		Query query = new Query();
		query.with(new Sort(Direction.DESC, "date"));
		
		List<StationDao> latestStationStatuses = mongoTemplate.find(query, StationDao.class);
	    
		
		return latestStationStatuses;
	}	
}
