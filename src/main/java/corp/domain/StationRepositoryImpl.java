package corp.domain;

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

import corp.businessModel.Tank;
import corp.services.Utils;

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
		} else if (dateEnd.equalsIgnoreCase("latest") && Utils.isNumeric(dateBeg)) {
			query = new Query(Criteria.where("date").gte(getEarliestBalanceTimestampNDaysAgo(-Integer.valueOf(dateBeg))));
		}
		
		//query.with(new Sort(Direction.DESC, "$natural"));
		query.with(new Sort(Direction.DESC, "date"));
		
		List<StationDao> latestStationStatuses = mongoTemplate.find(query, StationDao.class);
		
		if (latestStationStatuses.size() > 1 && backDataCount != 0) {
			latestStationStatuses = latestStationStatuses.subList(backDataCount, backDataCount + 2);
		}
		
		// Order Tanks
		if(latestStationStatuses.size() <= 2) {
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
		}
		
		return latestStationStatuses.size() == 0 ? null: latestStationStatuses;
	}

	private Date getEarliestBalanceTimestampNDaysAgo(Integer nDaysAgo) {
		Date earliestBalanceTimestampInShiftDates = null;
		Date dateNDaysAgo = Utils.getDateAtMidnightNDaysAgo(nDaysAgo);
		String dateNDaysAgoFullFormat = Utils.formatDate(dateNDaysAgo, "dd/MM/yyyy");
		String dateNDaysAgoShortFormat = Utils.formatDate(dateNDaysAgo, "d/M/yyyy");
		
		Query shiftDateQuery = new Query(getShiftCriteriaByTwoFormatDates(dateNDaysAgoFullFormat, dateNDaysAgoShortFormat));
		shiftDateQuery.with(new Sort(Direction.ASC, "date"));
		List<StationDao> stationBalancesByShiftDate = mongoTemplate.find(shiftDateQuery, StationDao.class);
		
		if (stationBalancesByShiftDate.size() > 0) {
			earliestBalanceTimestampInShiftDates = stationBalancesByShiftDate.get(0).getDate();
		} else {
			earliestBalanceTimestampInShiftDates = dateNDaysAgo; 
		}
		return earliestBalanceTimestampInShiftDates;
	}
	
	public Criteria getShiftCriteriaByTwoFormatDates(String shiftDate1, String shiftDate2) {
		Criteria shiftDate1Criteria = Criteria.where("shiftDate").is(shiftDate1);
		Criteria shiftDate2Criteria = Criteria.where("shiftDate").is(shiftDate2);
		return new Criteria().orOperator(shiftDate1Criteria, shiftDate2Criteria);
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
