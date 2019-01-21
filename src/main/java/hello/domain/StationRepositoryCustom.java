package hello.domain;

import java.util.List;

public interface StationRepositoryCustom {
	
	 List<StationDao> findLatest(String dateEnd, String dateBeg, int backDataCount);
	 
	 List<StationDao> findLatestMonth();

}
