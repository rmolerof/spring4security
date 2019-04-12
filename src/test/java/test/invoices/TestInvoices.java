package test.invoices;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import corp.Application;
import corp.domain.InvoiceDao;
import corp.domain.InvoicesRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestInvoices {
	
	private static Logger logger = LogManager.getLogger(TestInvoices.class);
	
	@Autowired
	private InvoicesRepository invoicesRepository;
	
	
	
	@Test
	public void cleanupBonusByBonusNumber() {
		
		String bonusNumber = "7027661000000000000";
		List<InvoiceDao> invoiceDaos = invoicesRepository.findInvoicesByBonusNumber(bonusNumber);
		int count = 0;
		for (InvoiceDao invoiceDao: invoiceDaos) {
			
			logger.info(++count + " updated: " + invoiceDao.getInvoiceNumber() + ", "  + invoiceDao.getBonusNumber() + ", " + invoiceDao.getBonusStatus());
			invoiceDao.setBonusNumber("");
			invoiceDao.setBonusStatus("");
			invoicesRepository.save(invoiceDao);
		}
	}
}
