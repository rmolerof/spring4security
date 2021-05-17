package test.invoices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	@Ignore
	@Test
	public void cleanupBonusByBonusNumberList() throws ParseException {
		
		String untilDateStr = "30/10/2019";
		Date untilDate = new SimpleDateFormat("dd/MM/yyyy").parse(untilDateStr);  
		
		List<InvoiceDao> invoiceDaos = invoicesRepository.findAllPendingInvoicesTillDateForBonus(untilDate, new Sort(Sort.Direction.ASC, "date"));
		
		int count = 0;
		for (InvoiceDao invoiceDao: invoiceDaos) {
			
			logger.info(++count + " updated: " + invoiceDao.getInvoiceNumber() + ", "  + invoiceDao.getBonusNumber() + ", " + invoiceDao.getBonusStatus());
			//invoiceDao.setBonusNumber("");
			invoiceDao.setBonusStatus("ENVIADO");
			invoicesRepository.save(invoiceDao);
		}
	}
	
	@Ignore
	@Test
	public void submitBonusFromDateTillDateTest() {

		Date fromDate = Utils.getDateAtMidnightNDaysAgo(15, globalProperties.getTimeZoneID());
		Date untilDate = Utils.getDateAtMidnightNDaysAgo(14, globalProperties.getTimeZoneID());
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
	
	@Ignore
	@Test
	public void recoverHashCodeFromDateTillDateTest() throws ParseException {

		Date fromDate = new SimpleDateFormat("dd/MM/yyyy").parse("11/05/2021"); 
		Date untilDate = new SimpleDateFormat("dd/MM/yyyy").parse("13/05/2021");
		
		List<InvoiceDao> invoiceDaos = invoicesRepository.findAllInvoicesForSunatFromDateTillDate(fromDate, untilDate, new Sort(Sort.Direction.ASC, "date"));
		invoiceDaos.removeIf(invoiceDao -> !invoiceDao.getInvoiceHash().trim().equals(""));
		
		userService.processInvoicesBySunat(invoiceDaos);
		
	}
	
	@Test
	@Ignore
	public void manipulateSunatFromDateTillDateTest() throws ParseException {
		 
		//Date fromDate = Utils.getDateAtMidnightNDaysAgo(16, globalProperties.getTimeZoneID());
		//Date untilDate = Utils.getDateAtMidnightNDaysAgo(14, globalProperties.getTimeZoneID());
		
		Date fromDate = new SimpleDateFormat("dd/MM/yyyy").parse("30/12/2020"); 
		Date untilDate = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/20"); 
		List<InvoiceDao> invoiceDaos = invoicesRepository.findAllInvoicesForSunatFromDateTillDate(fromDate, untilDate, new Sort(Sort.Direction.ASC, "date"));
		
		int sunatCount = 0;
		for (InvoiceDao invoiceDao: invoiceDaos) {
			if (invoiceDao.getSunatStatus().equals("PENDIENTE")) {
				logger.info(++sunatCount + " Sunat Set Manually, date: " + invoiceDao.getDate() + ", number: " + invoiceDao.getInvoiceNumber() + ", status: " + invoiceDao.getSunatStatus());
				invoiceDao.setSunatStatus("ENVIADO");
				invoicesRepository.save(invoiceDao);
			}
		}
		
	}
	
	@Test
	@Ignore
	public void resetSunatFromDateTillDateTest() {

		Date untilDate = Utils.getDateAtMidnightNDaysAgo(2, globalProperties.getTimeZoneID());
		List<InvoiceDao> invoiceDaos = invoicesRepository.resetAllInvoicesForSunatTillDate(untilDate, new Sort(Sort.Direction.ASC, "date"));
		
		int sunatCount = 0;
		for (InvoiceDao invoiceDao: invoiceDaos) {
			
			logger.info(++sunatCount + " Sunat Sent Manually: " + invoiceDao.getInvoiceNumber() + ", Status: " + invoiceDao.getSunatStatus());
			invoiceDao.setSunatStatus("PENDIENTE");
			invoicesRepository.save(invoiceDao);
		}
		
	}
}
