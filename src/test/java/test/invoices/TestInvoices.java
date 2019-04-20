package test.invoices;

import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import corp.Application;
import corp.domain.InvoiceDao;
import corp.domain.InvoicesRepository;
import corp.services.ApplicationService;
import corp.services.GlobalProperties;
import corp.services.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestInvoices {
	
	private static Logger logger = LogManager.getLogger(TestInvoices.class);
	
	@Autowired
	private InvoicesRepository invoicesRepository;
	@Autowired
	private GlobalProperties globalProperties;
	@Autowired
	ApplicationService userService;
	
	@Ignore
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
	
	@Test
	public void submitBonusFromDateTillDateTest() {

		Date fromDate = Utils.getDateAtMidnightNDaysAgo(13, globalProperties.getTimeZoneID());
		Date untilDate = Utils.getDateAtMidnightNDaysAgo(8, globalProperties.getTimeZoneID());
		List<InvoiceDao> invoiceDaos = invoicesRepository.findAllInvoicesWithBonusNumberFromDateTillDate(fromDate, untilDate, new Sort(Sort.Direction.ASC, "date"));
		
		/*int count = 0;
		for (InvoiceDao invoiceDao: invoiceDaos) {
			
			logger.info(++count + " Bonus Sent Manually: " + invoiceDao.getInvoiceNumber() + ", "  + invoiceDao.getBonusNumber() + ", " + invoiceDao.getBonusStatus());
			invoiceDao.setBonusStatus("PENDIENTE");
			invoiceDao.setBonusAccumulatedPoints("");
			invoicesRepository.save(invoiceDao);
		}*/
		
		userService.processInvoicesByBonus(invoiceDaos);
	}
}
