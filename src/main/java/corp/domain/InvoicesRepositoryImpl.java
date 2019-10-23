package corp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import corp.services.ApplicationService;
import corp.services.GlobalProperties;
import corp.services.Utils;

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
		} else if (loadInvoiceAmountCriteria.contains("MONTH")) {
			return findTotalInvoicesByMonth(Integer.parseInt(loadInvoiceAmountCriteria.substring(0, 2)), voidedInvoicesIncluded);
		}
		
		return new ArrayList<InvoiceDao>();
	}
	
	public List<InvoiceDao> findInvoicesByAmountCriteriaAndVoidedIncludedFlagForBonus(String loadInvoiceAmountCriteria, boolean voidedInvoicesIncluded) {
		
		if (isNumeric(loadInvoiceAmountCriteria)) {
			return findLatestInvoicesByQuantity(Integer.valueOf(loadInvoiceAmountCriteria));
		} else if (loadInvoiceAmountCriteria.equals(InvoicesRepository.TOTAL_INVOICES_TODAY)) {
			return findTotalInvoicesTodayForBonus(voidedInvoicesIncluded);
		} else if (loadInvoiceAmountCriteria.equals(InvoicesRepository.TOTAL_INVOICES_LAST7DAYS)) {
			return findTotalInvoicesLastNdaysForBonus(InvoicesRepository.WEEK, voidedInvoicesIncluded);
		} else if (loadInvoiceAmountCriteria.equals(InvoicesRepository.TOTAL_PENDING_INVOICES)) {
			return findLatestPendingInvoicesForBonus(voidedInvoicesIncluded);
		} else if (loadInvoiceAmountCriteria.equals(InvoicesRepository.TOTAL_INVOICES_MONTH)) {
			return findTotalInvoicesLastNdaysForBonus(InvoicesRepository.MONTH, voidedInvoicesIncluded);
		} else if (loadInvoiceAmountCriteria.equals(InvoicesRepository.TOTAL_INVOICES_YEAR)) {
			return findTotalInvoicesCurrentYearForBonus(voidedInvoicesIncluded);
		} else if (loadInvoiceAmountCriteria.contains("MONTH")) {
			return findTotalInvoicesByMonthForBonus(Integer.parseInt(loadInvoiceAmountCriteria.substring(0, 2)), voidedInvoicesIncluded);
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
		Criteria criteria = Criteria.where("date").gte(Utils.getTodayDateAtMidnight(globalProperties.getTimeZoneID()));
		
		if (!isVoidedInvoicesIncluded) {
			criteria = criteria.and("sunatStatus").ne(ApplicationService.VOIDED_STATUS);
		}
		
		return findInvoicesByCriteria(criteria, new Sort(Direction.DESC, "date"));
	}
	
	public List<InvoiceDao> findTotalInvoicesTodayForBonus(boolean isVoidedInvoicesIncluded) {
		Criteria criteria = Criteria.where("date").gte(Utils.getTodayDateAtMidnight(globalProperties.getTimeZoneID())).and("bonusNumber").ne("");
		
		if (!isVoidedInvoicesIncluded) {
			criteria = criteria.and("sunatStatus").ne(ApplicationService.VOIDED_STATUS);
		}
		
		return findInvoicesByCriteria(criteria, new Sort(Direction.DESC, "date"));
	}
	
	public List<InvoiceDao> findTotalInvoicesCurrentYear(boolean isVoidedInvoicesIncluded) {
		Criteria criteria = Criteria.where("date").gte(Utils.getDateAtMidnightByDayOfYear(1, globalProperties.getTimeZoneID()));
		
		if (!isVoidedInvoicesIncluded) {
			criteria = criteria.and("sunatStatus").ne(ApplicationService.VOIDED_STATUS);
		}
		
		return findInvoicesByCriteria(criteria, new Sort(Direction.DESC, "date"));
	}
	
	public List<InvoiceDao> findTotalInvoicesCurrentYearForBonus(boolean isVoidedInvoicesIncluded) {
		Criteria criteria = Criteria.where("date").gte(Utils.getDateAtMidnightByDayOfYear(1, globalProperties.getTimeZoneID())).and("bonusNumber").ne("");
		
		if (!isVoidedInvoicesIncluded) {
			criteria = criteria.and("sunatStatus").ne(ApplicationService.VOIDED_STATUS);
		}
		
		return findInvoicesByCriteria(criteria, new Sort(Direction.DESC, "date"));
	}
	
	public List<InvoiceDao> findTotalInvoicesLastNdays(Integer numberOfDaysAgo, boolean isVoidedInvoicesIncluded) {
		Criteria criteria = Criteria.where("date").gte(Utils.getDateAtMidnightNDaysAgo(numberOfDaysAgo, globalProperties.getTimeZoneID()));
		
		if (!isVoidedInvoicesIncluded) {
			criteria = criteria.and("sunatStatus").ne(ApplicationService.VOIDED_STATUS);
		}
		
		return findInvoicesByCriteria(criteria, new Sort(Direction.DESC, "date"));
	}
	
	public List<InvoiceDao> findTotalInvoicesByMonth(Integer monthNumber, boolean isVoidedInvoicesIncluded) {
		Criteria criteria = Criteria.where("date").gte(Utils.getDateBeginningOfMonth(monthNumber, globalProperties.getTimeZoneID())).lt(Utils.getDateBeginningOfNextMonth(monthNumber, globalProperties.getTimeZoneID()));
		
		if (!isVoidedInvoicesIncluded) {
			criteria = criteria.and("sunatStatus").ne(ApplicationService.VOIDED_STATUS);
		}
		
		return findInvoicesByCriteria(criteria, new Sort(Direction.DESC, "date"));
	}
	
	public List<InvoiceDao> findTotalInvoicesLastNdaysForBonus(Integer numberOfDaysAgo, boolean isVoidedInvoicesIncluded) {
		Criteria criteria = Criteria.where("date").gte(Utils.getDateAtMidnightNDaysAgo(numberOfDaysAgo, globalProperties.getTimeZoneID())).and("bonusNumber").ne("");
		
		if (!isVoidedInvoicesIncluded) {
			criteria = criteria.and("sunatStatus").ne(ApplicationService.VOIDED_STATUS);
		}
		
		return findInvoicesByCriteria(criteria, new Sort(Direction.DESC, "date"));
	}
	
	public List<InvoiceDao> findTotalInvoicesByMonthForBonus(Integer monthNumber, boolean isVoidedInvoicesIncluded) {
		Criteria criteria = Criteria.where("date").gte(Utils.getDateBeginningOfMonth(monthNumber, globalProperties.getTimeZoneID())).lt(Utils.getDateBeginningOfNextMonth(monthNumber, globalProperties.getTimeZoneID())).and("bonusNumber").ne("");
		
		if (!isVoidedInvoicesIncluded) {
			criteria = criteria.and("sunatStatus").ne(ApplicationService.VOIDED_STATUS);
		}
		
		return findInvoicesByCriteria(criteria, new Sort(Direction.DESC, "date"));
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
		Criteria notSentInvoicesCriteria = Criteria.where("sunatStatus").ne(ApplicationService.SENT_STATUS);
		
		if (!isVoidedInvoicesIncluded) {
			Criteria notVoidedInvoicesCriteria = Criteria.where("sunatStatus").ne(ApplicationService.VOIDED_STATUS);
			criteria = new Criteria().andOperator(notSentInvoicesCriteria, notVoidedInvoicesCriteria);
		} else {
			criteria = notSentInvoicesCriteria;
		}
		
		return findInvoicesByCriteria(criteria, new Sort(Direction.DESC, "date"));
	}
	
	public List<InvoiceDao> findLatestPendingInvoicesForBonus(boolean isVoidedInvoicesIncluded) {
		
		Criteria criteria = null;
		Criteria notSentInvoicesCriteria = Criteria.where("bonusStatus").is(ApplicationService.PENDING_STATUS);
		
		if (isVoidedInvoicesIncluded) {
			Criteria notVoidedInvoicesCriteria = Criteria.where("sunatStatus").is(ApplicationService.VOIDED_STATUS);
			criteria = new Criteria().orOperator(notSentInvoicesCriteria, notVoidedInvoicesCriteria);
		} else {
			criteria = notSentInvoicesCriteria;
		}
		
		return findInvoicesByCriteria(criteria, new Sort(Direction.DESC, "date"));
	}
	
	public List<InvoiceDao> findInvoicesByCriteria(Criteria criteria, Sort sort) {
		Query query = new Query(criteria);
		query.with(sort);
		
		List<InvoiceDao> invoiceDaos = mongoTemplate.find(query, InvoiceDao.class);

		return invoiceDaos;
	}
	
	public InvoiceDao findLastSentInvoice(String invoiceType) {
		
		Query query = null;
		Criteria invoiceTypeCriteria = null;
		Criteria notaDeCreditoOfInvoiceTypeCriteria = null;
		
		if (invoiceType.equals(ApplicationService.BOLETA)) {
			invoiceTypeCriteria = Criteria.where("sunatStatus").is(ApplicationService.SENT_STATUS).and("invoiceType").is(ApplicationService.BOLETA);
			notaDeCreditoOfInvoiceTypeCriteria = Criteria.where("sunatStatus").is(ApplicationService.SENT_STATUS).and("invoiceTypeModified").is(ApplicationService.BOLETA);
			query = new Query(new Criteria().orOperator(invoiceTypeCriteria, notaDeCreditoOfInvoiceTypeCriteria));
		} else {
			invoiceTypeCriteria = Criteria.where("sunatStatus").is(ApplicationService.SENT_STATUS).and("invoiceType").is(ApplicationService.FACTURA);
			notaDeCreditoOfInvoiceTypeCriteria = Criteria.where("sunatStatus").is(ApplicationService.SENT_STATUS).and("invoiceTypeModified").is(ApplicationService.FACTURA);
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
			invoiceTypeCriteria = Criteria.where("sunatStatus").ne(ApplicationService.VOIDED_STATUS).and("invoiceType").is(ApplicationService.BOLETA);
			notaDeCreditoOfInvoiceTypeCriteria = Criteria.where("sunatStatus").ne(ApplicationService.VOIDED_STATUS).and("invoiceTypeModified").is(ApplicationService.BOLETA);
			query = new Query(new Criteria().orOperator(invoiceTypeCriteria, notaDeCreditoOfInvoiceTypeCriteria));
		} else {
			invoiceTypeCriteria = Criteria.where("sunatStatus").ne(ApplicationService.VOIDED_STATUS).and("invoiceType").is(ApplicationService.FACTURA);
			notaDeCreditoOfInvoiceTypeCriteria = Criteria.where("sunatStatus").ne(ApplicationService.VOIDED_STATUS).and("invoiceTypeModified").is(ApplicationService.FACTURA);
			query = new Query(new Criteria().orOperator(invoiceTypeCriteria, notaDeCreditoOfInvoiceTypeCriteria));
		}
		
		query.limit(1);
		query.with(new Sort(Direction.DESC, "date"));
		
		List<InvoiceDao> invoiceDaos = mongoTemplate.find(query, InvoiceDao.class);
		
		return invoiceDaos.size() > 0 ? invoiceDaos.get(0): InvoiceDao.NOT_FOUND;
	}

	public InvoiceDao findFirstByInvoiceNumberNotVoided(String invoiceNumber) {
		
		Query query = new Query(Criteria.where("sunatStatus").ne(ApplicationService.VOIDED_STATUS).and("invoiceNumber").is(invoiceNumber));
		query.limit(1);
		query.with(new Sort(Direction.DESC, "date"));
		
		List<InvoiceDao> invoiceDaos = mongoTemplate.find(query, InvoiceDao.class);
		return invoiceDaos.size() > 0 ? invoiceDaos.get(0): InvoiceDao.NOT_FOUND;
	}

	public List<InvoiceDao> findAllPendingInvoicesTillDate(Date processPendingInvoicesTillDate, Sort sort) {

		Criteria pendingInvoicesCriteriaTillDate = Criteria.where("sunatStatus").is(ApplicationService.PENDING_STATUS).and("date").lt(Utils.transformZoneToGMTDate(Utils.addNDaysToDate(processPendingInvoicesTillDate, 1), globalProperties.getTimeZoneID()));
		
		return findInvoicesByCriteria(pendingInvoicesCriteriaTillDate, sort);
	}

	public List<InvoiceDao> findAllPendingInvoicesTillDateForBonus(Date processPendingInvoicesTillDate, Sort sort) {
		
		Criteria pendingInvoicesCriteriaTillDate = Criteria.where("bonusStatus").is(ApplicationService.PENDING_STATUS).and("date").lt(Utils.transformZoneToGMTDate(Utils.addNDaysToDate(processPendingInvoicesTillDate, 1), globalProperties.getTimeZoneID()));
		
		return findInvoicesByCriteria(pendingInvoicesCriteriaTillDate, sort);
	}
	
	public List<InvoiceDao> findAllInvoicesWithBonusNumberFromDateTillDate(Date fromDate, Date tillDate, Sort sort) {
		
		Date fromDateLocal = Utils.transformZoneToGMTDate(Utils.addNDaysToDate(fromDate, 1), globalProperties.getTimeZoneID());
		Date tillDateLocal = Utils.transformZoneToGMTDate(Utils.addNDaysToDate(tillDate, 1), globalProperties.getTimeZoneID());
		
		//Criteria pendingInvoicesCriteriaTillDate = Criteria.where("bonusStatus").is("").and("bonusNumber").ne("").and("date").gte(fromDateLocal).lt(tillDateLocal);
		Criteria pendingInvoicesCriteriaTillDate = Criteria.where("bonusNumber").ne("").and("date").gte(fromDateLocal).lt(tillDateLocal);
		
		return findInvoicesByCriteria(pendingInvoicesCriteriaTillDate, sort);
	}
	
	public List<InvoiceDao> findAllInvoicesForSunatFromDateTillDate(Date fromDate, Date tillDate, Sort sort) {
		
		Date fromDateLocal = Utils.transformZoneToGMTDate(Utils.addNDaysToDate(fromDate, 1), globalProperties.getTimeZoneID());
		Date tillDateLocal = Utils.transformZoneToGMTDate(Utils.addNDaysToDate(tillDate, 1), globalProperties.getTimeZoneID());
		
		Criteria pendingInvoicesCriteriaTillDate = Criteria.where("date").gte(fromDateLocal).lt(tillDateLocal);
		
		return findInvoicesByCriteria(pendingInvoicesCriteriaTillDate, sort);
	}
	
	public List<InvoiceDao> resetAllInvoicesForSunatTillDate(Date tillDate, Sort sort) {
		
		Date tillDateLocal = Utils.transformZoneToGMTDate(Utils.addNDaysToDate(tillDate, 1), globalProperties.getTimeZoneID());
		Date fromDateLocal = Utils.transformZoneToGMTDate(Utils.addNDaysToDate(Utils.getDateAtMidnightNDaysAgo(30, globalProperties.getTimeZoneID()), 1), globalProperties.getTimeZoneID());
		
		Criteria pendingInvoicesCriteriaTillDate = Criteria.where("date").gte(fromDateLocal).lt(tillDateLocal).and("sunatStatus").is(ApplicationService.SENT_STATUS).and("invoiceHash").is("");
		
		return findInvoicesByCriteria(pendingInvoicesCriteriaTillDate, sort);
	}

}
