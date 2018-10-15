package hello.domain;

import java.util.List;

public interface GasPricesRepositoryCustom {
	
	 List<GasPricesDao> findLatest(String dateEnd, String dateBeg);

}
