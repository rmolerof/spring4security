package corp.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import corp.services.ApplicationService;
import corp.services.GlobalProperties;
import corp.sunat.XmlSunat;

public class InvoicesRepositoryImpl implements InvoicesRepositoryCustom {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	private GlobalProperties globalProperties;
	
	public List<InvoiceDao> findInvoicesByAmountCriteriaAndVoidedIncludedFlag(String loadInvoiceAmountCriteria, boolean voidedInvoicesIncluded) {
		
		if (isNumeric(loadInvoiceAmountCriteria)) {
			return findLatestInvoicesByQuantity(Integer.valueOf(loadInvoiceAmountCriteria));
		} else if (loadInvoiceAmountCriteria.equals(InvoicesRepository.TOTAL_INVOICES_TODAY)) {
			return findTotalInvoicesToday(voidedInvoicesIncluded);
		} else if (loadInvoiceAmountCriteria.equals(InvoicesRepository.TOTAL_INVOICES_LAST7DAYS)) {
			return findTotalInvoicesLastNdays(InvoicesRepository.WEEK, voidedInvoicesIncluded);
		} else if (loadInvoiceAmountCriteria.equals(InvoicesRepository.TOTAL_PENDING_INVOICES)) {
			return findLatestPendingInvoices(voidedInvoicesIncluded);
		} else if (loadInvoiceAmountCriteria.equals(InvoicesRepository.TOTAL_INVOICES_MONTH)) {
			return findTotalInvoicesLastNdays(InvoicesRepository.MONTH, voidedInvoicesIncluded);
		} else if (loadInvoiceAmountCriteria.equals(InvoicesRepository.TOTAL_INVOICES_YEAR)) {
			return findTotalInvoicesCurrentYear(voidedInvoicesIncluded);
		}
		
		return new ArrayList<InvoiceDao>();
	}
	
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public List<InvoiceDao> findTotalInvoicesToday(boolean isVoidedInvoicesIncluded) {
		Criteria criteria = Criteria.where("date").gte(XmlSunat.transformZoneToGMTDate(getTodayDateAtMidnight(), globalProperties.getTimeZoneID()));
		
		if (!isVoidedInvoicesIncluded) {
			criteria = criteria.and("sunatStatus").ne(ApplicationService.SUNAT_VOIDED_STATUS);
		}
		
		return findInvoicesByCriteria(criteria);
	}
	
	public List<InvoiceDao> findTotalInvoicesCurrentYear(boolean isVoidedInvoicesIncluded) {
		Criteria criteria = Criteria.where("date").gte(XmlSunat.transformZoneToGMTDate(getDateAtMidnightByDayOfYear(1), globalProperties.getTimeZoneID()));
		
		if (!isVoidedInvoicesIncluded) {
			criteria = criteria.and("sunatStatus").ne(ApplicationService.SUNAT_VOIDED_STATUS);
		}
		
		return findInvoicesByCriteria(criteria);
	}
	
	public List<InvoiceDao> findTotalInvoicesLastNdays(Integer numberOfDaysAgo, boolean isVoidedInvoicesIncluded) {
		Criteria criteria = Criteria.where("date").gte(XmlSunat.transformZoneToGMTDate(getDateAtMidnightNDaysAgo(numberOfDaysAgo), globalProperties.getTimeZoneID()));
		
		if (!isVoidedInvoicesIncluded) {
			criteria = criteria.and("sunatStatus").ne(ApplicationService.SUNAT_VOIDED_STATUS);
		}
		
		return findInvoicesByCriteria(criteria);
	}
	
	public Date getTodayDateAtMidnight() {
		return getDateAtMidnightNDaysAgo(0);
	}
	
	public Date getDateAtMidnightNDaysAgo(Integer numberOfDaysAgo) {
		Calendar date = getCurrentCalendarAtMidnight();
		date.add(Calendar.DAY_OF_MONTH, -numberOfDaysAgo);

		return date.getTime();
	}
	
	public Date addNDaysToDate(Date date, Integer nbrOfDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, nbrOfDays);
		
		return calendar.getTime();
	}
	
	public Date getDateAtMidnightByDayOfYear(Integer dayOfTheYear) {
		Calendar date = getCurrentCalendarAtMidnight();
		date.set(Calendar.DAY_OF_YEAR, dayOfTheYear);

		return date.getTime();
	}
	
	public Calendar getCurrentCalendarAtMidnight() {
		Calendar date = new GregorianCalendar();
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		return date;
	}
	
	public List<InvoiceDao> findLatestInvoicesByQuantity(Integer quantity) {
		Query query = new Query();
		query.limit(Math.abs(quantity));
		query.with(new Sort(Direction.DESC, "date"));
		
		List<InvoiceDao> invoiceDaos = mongoTemplate.find(query, InvoiceDao.class);
		
		return invoiceDaos;
	}
	
	public List<InvoiceDao> findLatestPendingInvoices(boolean isVoidedInvoicesIncluded) {
		
		Criteria criteria = null;
		Criteria notSentInvoicesCriteria = Criteria.where("sunatStatus").ne(ApplicationService.SUNAT_SENT_STATUS);
		
		if (!isVoidedInvoicesIncluded) {
			Criteria notVoidedInvoicesCriteria = Criteria.where("sunatStatus").ne(ApplicationService.SUNAT_VOIDED_STATUS);
			criteria = new Criteria().andOperator(notSentInvoicesCriteria, notVoidedInvoicesCriteria);
		}
		
		return findInvoicesByCriteria(criteria);
	}
	
	public List<InvoiceDao> findInvoicesByCriteria(Criteria criteria) {
		Query query = new Query(criteria);
		query.with(new Sort(Direction.DESC, "date"));
		
		List<InvoiceDao> invoiceDaos = mongoTemplate.find(query, InvoiceDao.class);

		return invoiceDaos;
	}
	
	public InvoiceDao findLastSentInvoice(String invoiceType) {
		
		Query query = null;
		Criteria invoiceTypeCriteria = null;
		Criteria notaDeCreditoOfInvoiceTypeCriteria = null;
		
		if (invoiceType.equals(ApplicationService.BOLETA)) {
			invoiceTypeCriteria = Criteria.where("sunatStatus").is(ApplicationService.SUNAT_SENT_STATUS).and("invoiceType").is(ApplicationService.BOLETA);
			notaDeCreditoOfInvoiceTypeCriteria = Criteria.where("sunatStatus").is(ApplicationService.SUNAT_SENT_STATUS).and("invoiceTypeModified").is(ApplicationService.BOLETA);
			query = new Query(new Criteria().orOperator(invoiceTypeCriteria, notaDeCreditoOfInvoiceTypeCriteria));
		} else {
			invoiceTypeCriteria = Criteria.where("sunatStatus").is(ApplicationService.SUNAT_SENT_STATUS).and("invoiceType").is(ApplicationService.FACTURA);
			notaDeCreditoOfInvoiceTypeCriteria = Criteria.where("sunatStatus").is(ApplicationService.SUNAT_SENT_STATUS).and("invoiceTypeModified").is(ApplicationService.FACTURA);
			query = new Query(new Criteria().orOperator(invoiceTypeCriteria, notaDeCreditoOfInvoiceTypeCriteria));
		}
		
		query.limit(1);
		query.with(new Sort(Direction.DESC, "date"));
		
		List<InvoiceDao> invoiceDaos = mongoTemplate.find(query, InvoiceDao.class);
		
		return invoiceDaos.size() > 0 ? invoiceDaos.get(0): InvoiceDao.NOT_FOUND;
	}
	
	public InvoiceDao findLastNotVoidedInvoice(String invoiceType) {
		
		Query query = null;
		Criteria invoiceTypeCriteria = null;
		Criteria notaDeCreditoOfInvoiceTypeCriteria = null;
		
		if (invoiceType.equals(ApplicationService.BOLETA)) {
			invoiceTypeCriteria = Criteria.where("sunatStatus").ne(ApplicationService.SUNAT_VOIDED_STATUS).and("invoiceType").is(ApplicationService.BOLETA);
			notaDeCreditoOfInvoiceTypeCriteria = Criteria.where("sunatStatus").ne(ApplicationService.SUNAT_VOIDED_STATUS).and("invoiceTypeModified").is(ApplicationService.BOLETA);
			query = new Query(new Criteria().orOperator(invoiceTypeCriteria, notaDeCreditoOfInvoiceTypeCriteria));
		} else {
			invoiceTypeCriteria = Criteria.where("sunatStatus").ne(ApplicationService.SUNAT_VOIDED_STATUS).and("invoiceType").is(ApplicationService.FACTURA);
			notaDeCreditoOfInvoiceTypeCriteria = Criteria.where("sunatStatus").ne(ApplicationService.SUNAT_VOIDED_STATUS).and("invoiceTypeModified").is(ApplicationService.FACTURA);
			query = new Query(new Criteria().orOperator(invoiceTypeCriteria, notaDeCreditoOfInvoiceTypeCriteria));
		}
		
		query.limit(1);
		query.with(new Sort(Direction.DESC, "date"));
		
		List<InvoiceDao> invoiceDaos = mongoTemplate.find(query, InvoiceDao.class);
		
		return invoiceDaos.size() > 0 ? invoiceDaos.get(0): InvoiceDao.NOT_FOUND;
	}

	public InvoiceDao findFirstByInvoiceNumberNotVoided(String invoiceNumber) {
		
		Query query = new Query(Criteria.where("sunatStatus").ne(ApplicationService.SUNAT_VOIDED_STATUS).and("invoiceNumber").is(invoiceNumber));
		query.limit(1);
		query.with(new Sort(Direction.DESC, "date"));
		
		List<InvoiceDao> invoiceDaos = mongoTemplate.find(query, InvoiceDao.class);
		return invoiceDaos.size() > 0 ? invoiceDaos.get(0): InvoiceDao.NOT_FOUND;
	}

	public List<InvoiceDao> findAllPendingInvoicesTillDate(Date processPendingInvoicesTillDate) {

		Criteria pendingInvoicesCriteriaTillDate = Criteria.where("sunatStatus").is(ApplicationService.SUNAT_PENDING_STATUS).and("date").lt(XmlSunat.transformZoneToGMTDate(addNDaysToDate(processPendingInvoicesTillDate, 1), globalProperties.getTimeZoneID()));
		
		return findInvoicesByCriteria(pendingInvoicesCriteriaTillDate);
	}

}
