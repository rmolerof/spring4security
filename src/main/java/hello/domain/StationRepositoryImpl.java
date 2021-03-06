package hello.domain;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import hello.businessModel.Tank;

public class StationRepositoryImpl implements StationRepositoryCustom {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public List<StationDao> findLatest(String dateEnd, String dateBeg, int backDataCount) {
		
		Query query = new Query();
		if (dateEnd.equalsIgnoreCase("latest") && dateBeg.equalsIgnoreCase("")) { 
			query.limit(1);
		} else if (dateEnd.equalsIgnoreCase("latest") && dateBeg.equalsIgnoreCase("previous") && backDataCount >=0) {
			query.limit(backDataCount + 2);
		}
		//query.with(new Sort(Direction.DESC, "$natural"));
		query.with(new Sort(Direction.DESC, "date"));
		
		List<StationDao> latestStationStatuses = mongoTemplate.find(query, StationDao.class);
		
		if (latestStationStatuses.size() > 1) {
			latestStationStatuses = latestStationStatuses.subList(backDataCount, backDataCount + 2);
		}
		
		// Order Tanks
		Map<String, Tank> newTanks = new LinkedHashMap<String, Tank>();
		for (StationDao station: latestStationStatuses) {
			Map<String, Tank> tanks = station.getTanks();
			
			String key = "d2";
			if (tanks.containsKey(key)) {
				newTanks.put(key, tanks.get(key));
			}
			key = "g90";
			if (tanks.containsKey(key)) {
				newTanks.put(key, tanks.get(key));
			}
			key = "g95";
			if (tanks.containsKey(key)) {
				newTanks.put(key, tanks.get(key));
			}
			
			station.setTanks(newTanks);
		}
		
		return latestStationStatuses.size() == 0 ? null: latestStationStatuses;
	}

	@Override
	public List<StationDao> findLatestMonth() {
		Query query = new Query();
		query.with(new Sort(Direction.DESC, "date"));
		
		List<StationDao> latestStationStatuses = mongoTemplate.find(query, StationDao.class);
	    
		
		return latestStationStatuses;
	}

	@Override
	public StationDao findFirstBeforeDate(Date date) {
		Query query = new Query();
		query.addCriteria(Criteria.where("date").lt(date));
		query.limit(1);
		query.with(new Sort(Direction.DESC, "date"));
		
		List<StationDao> firstStationBeforeDateGroup = mongoTemplate.find(query, StationDao.class);
		
		return firstStationBeforeDateGroup.size() > 0 ? firstStationBeforeDateGroup.get(0): StationDao.NOT_FOUND;
	}	
}
