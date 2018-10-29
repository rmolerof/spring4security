package hello.domain;

import java.util.List;

public interface InvoicesRepositoryCustom {

	List<InvoiceDao> findLatest(String dateEnd, String dateBeg);
	
}
