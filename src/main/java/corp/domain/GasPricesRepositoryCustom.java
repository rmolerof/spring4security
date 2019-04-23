package corp.domain;

import java.util.List;

public interface GasPricesRepositoryCustom {
	
	 List<GasPricesDao> findLatest(String dateEnd, String dateBeg);
	 
	 List<GasPricesDao> findLatestByName(String name);

}
