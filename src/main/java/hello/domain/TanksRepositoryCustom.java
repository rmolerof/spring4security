package hello.domain;

import java.util.List;

public interface TanksRepositoryCustom {
	
	 List<TanksDao> findLatest(String dateEnd, String dateBeg);

}
