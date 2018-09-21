package hello.domain;

import java.util.List;

public interface StationRepositoryCustom {
	
	 StationDao findLatest();
	 
	 List<StationDao> findLatestMonth();

}
