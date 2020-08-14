package corp.services;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import corp.businessModel.Dispenser;
import corp.businessModel.Station;
import corp.businessModel.Tank;
import corp.businessModel.TotalDay;
import corp.domain.DNIDao;
import corp.domain.DNIsRepository;
import corp.domain.InvoiceDao;
import corp.domain.InvoicesRepository;
import corp.domain.RUCDao;
import corp.domain.RUCsRepository;
import corp.domain.StationDao;
import corp.model.DayDataCriteria;
import corp.model.InvoiceVo;
import corp.reports.CustomJRDataSource;
import corp.sunat.XmlSunat;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class Utils {
	
	private static Logger logger = LogManager.getLogger(Utils.class);
	
	private String basePath;
	
	@Autowired
    private InvoicesRepository invoicesRepository;
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
    private RUCsRepository rucsRepository;
	@Autowired
    private DNIsRepository dnisRepository;
	@Autowired
	private GlobalProperties globalProperties;

	public StationDao updateStationDao(StationDao currentStation, DayDataCriteria dayDataCriteria) {
		
		TotalDay totalDay = processShift(currentStation.getDispensers(), currentStation.getTanks(), dayDataCriteria);
		
		// Update Station numbers
		StationDao newCurrentStationDao = currentStation;
		
		newCurrentStationDao.setPumpAttendantNames(dayDataCriteria.getPumpAttendantNames());
		newCurrentStationDao.setDate(dayDataCriteria.getDate());
		newCurrentStationDao.setShift(dayDataCriteria.getShift());
		newCurrentStationDao.setShiftDate(dayDataCriteria.getShiftDate());
		newCurrentStationDao.setTotalCash(dayDataCriteria.getTotalCash());
		newCurrentStationDao.setExpensesAndCredits(dayDataCriteria.getExpensesAndCredits());
		newCurrentStationDao.setTotalDay(totalDay);
		
		
		// Update gallons counter
		for (Entry<String, Dispenser> entry: newCurrentStationDao.getDispensers().entrySet()) {
			entry.getValue().setGallons(dayDataCriteria.getDayData().get(entry.getKey()));
		}
		
		// Update tanks' stock
		for (Entry<String, Tank> entry: newCurrentStationDao.getTanks().entrySet()) {
			entry.getValue().setGals(totalDay.getStockGals(entry.getKey()));
		}
		
		return newCurrentStationDao;
	}
	
	public TotalDay processShift(Map<String, Dispenser> dispensers, Map<String, Tank> tanks, DayDataCriteria dayDataCriteria) {
		
		TotalDay totalDay = new TotalDay();
		
		for (Entry<String, Dispenser> entry: dispensers.entrySet()) {
			
			double gallonsDiff = roundTwo((dayDataCriteria.getDayData().get(entry.getKey()) - entry.getValue().getGallons()));
			String name = entry.getKey().substring(0, entry.getKey().lastIndexOf("_"));
			totalDay.setTotalGalsSoldDay(name, toFixedTwo(totalDay.getTotalGalsSoldDay(name) + roundTwo(dayDataCriteria.getDayData().get(entry.getKey()) - entry.getValue().getGallons())));
			totalDay.setTotalSolesRevenueDay(name, toFixedTwo(totalDay.getTotalSolesRevenueDay(name) + toFixedTwo(gallonsDiff * entry.getValue().getPrice())));
			totalDay.setTotalSolesRevenueDay(roundTwo(totalDay.getTotalSolesRevenueDay() + toFixedTwo(gallonsDiff * entry.getValue().getPrice())));
			double priceDiff = roundTwo(entry.getValue().getPrice() - entry.getValue().getCost());
			totalDay.setTotalProfitDay(name, roundTwo(totalDay.getTotalProfitDay(name) + toFixedTwo(gallonsDiff * priceDiff)));
			totalDay.setTotalProfitDay(roundTwo(totalDay.getTotalProfitDay() + toFixedTwo(gallonsDiff * priceDiff)));
			totalDay.setStockGals(name, toFixedTwo(tanks.get(name).getGals() - totalDay.getTotalGalsSoldDay(name)));
		}
		
		return totalDay;
	}
	
	public Station updateStation(Station currentStation, DayDataCriteria dayDataCriteria) {
		
		TotalDay totalDay = processShift(currentStation.getDispensers(), currentStation.getTanks(), dayDataCriteria);
		
		// Update Station numbers
		Station newCurrentStation = new Station(currentStation);
		
		newCurrentStation.setPumpAttendantNames(dayDataCriteria.getPumpAttendantNames());
		newCurrentStation.setDate(dayDataCriteria.getDate());
		newCurrentStation.setShift(dayDataCriteria.getShift());
		newCurrentStation.setShiftDate(dayDataCriteria.getShiftDate());
		newCurrentStation.setTotalCash(dayDataCriteria.getTotalCash());
		newCurrentStation.setExpensesAndCredits(dayDataCriteria.getExpensesAndCredits());
		newCurrentStation.setTotalDay(totalDay);
		
		
		// Update gallons counter
		for (Entry<String, Dispenser> entry: newCurrentStation.getDispensers().entrySet()) {
			entry.getValue().setGallons(dayDataCriteria.getDayData().get(entry.getKey()));
		}
		
		// Update tanks' stock
		for (Entry<String, Tank> entry: newCurrentStation.getTanks().entrySet()) {
			entry.getValue().setGals(totalDay.getStockGals(entry.getKey()));
		}
		
		return newCurrentStation;
	}
	
	public String sendEmail(String to, String from, String subject, String body, List<String> attachmentPaths) {

		final String username = globalProperties.getEmailUsername();
		final String password = globalProperties.getEmailPassword();
		String host = globalProperties.getEmailHost();

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.enable", false);
		props.put("mail.smtp.starttls.enable", false);

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText(body);

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			for (String path: attachmentPaths) {
				addAttachment(multipart, path);
			}

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);

			logger.info("Email sent successfully to " + to + ". Subject: " + subject);
			
			return "1";
		} catch (MessagingException e) {
			logger.error("Error in " + Utils.class + "::sendEmail()", e);
			//throw new RuntimeException(e);
			return "0";
		}

	}

	private static void addAttachment(Multipart multipart, String filename) throws MessagingException {
		DataSource source = new FileDataSource(filename);
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(new File(filename).getName());
		multipart.addBodyPart(messageBodyPart);
	}
	
	public String generateReport(String invoiceNbr) {
		JasperReport jasperReport;
		JasperPrint jasperPrint;
		String pdfPath = getBasePath() + "/jasperReports/Comprobante_" + invoiceNbr + ".pdf";
		
		if (new File(pdfPath).isFile()) {
			logger.info("File found in local memory: " + pdfPath);
		} else {
			
			// Create path if basePath doesn't exist
			File f = new File(pdfPath);
		    if(!f.getParentFile().isDirectory()){
				f.getParentFile().mkdirs();
			}
		
			try {
				jasperReport = JasperCompileManager.compileReport(getBasePath() + "/certificatesAndTemplates/ticket-template.jrxml");
				
				InvoiceDao invoiceDao = invoicesRepository.findFirstByInvoiceNumberNotVoided(invoiceNbr);
				populateCompanyDetailsDao(invoiceDao, globalProperties);
				invoiceDao.setDate(Utils.transformGMTDateToZone(invoiceDao.getDate(), globalProperties.getTimeZoneID()));
				List<InvoiceDao> custList = Stream.of(invoiceDao).collect(Collectors.toList());
				
				CustomJRDataSource<InvoiceDao> dataSource = new CustomJRDataSource<InvoiceDao>().initBy(custList);
				jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<String, Object>(), dataSource);
				JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
			
			} catch(JRException e) {
				logger.error(e.getMessage());
				pdfPath = "";
			} catch(Exception e) {
				logger.error(e.getMessage());
				pdfPath = "";
			}
		}
		
		return pdfPath;
	}
	
	public String generateXMLFromDB(String invoiceNbr) {
		
		
		InvoiceDao invoiceDao = invoicesRepository.findFirstByInvoiceNumberNotVoided(invoiceNbr);
		InvoiceVo invoiceVo = new InvoiceVo(invoiceDao);
		invoiceVo.setMyRuc(globalProperties.getMyRuc());
		
		String xmlPath = getBasePath() + "/xmlsSunat/" + globalProperties.getMyRuc() + "-" + invoiceVo.getInvoiceType() + "-" + invoiceVo.getInvoiceNumber() + ".XML";
		
		if (new File(xmlPath).isFile()) {
			logger.info("File found in local memory: " + xmlPath);
		} else {
			
			try {
				
				XmlSunat.invokeSunat(invoiceVo, getBasePath());
				XmlSunat.firma(invoiceVo, getBasePath(), globalProperties.getSunatSignatureFileName(), globalProperties.getSunatSignaturePassword());
				
			} catch (Exception e) {
				logger.error(e.getMessage());
				xmlPath = "";
			}
		}
		
		return xmlPath;
	}
	
	public String saveCustomerEmail(String clientEmailAddress, String clientDocNumber, String clientDocType) {
		
		if (!clientDocNumber.equals("0")) {
			if (clientDocType.equals("1")) {
				
				// search DNI in DB
				DNIDao dniDao = dnisRepository.findFirstByDni(clientDocNumber);
				
				// if not Found, search in Sunat
				if (null == dniDao) {
					return "0";
				}
				
				dniDao.setCorreoElectronico(clientEmailAddress);
				dnisRepository.save(dniDao);
				return "1";
			} else {
	
				RUCDao rucDao = rucsRepository.findFirstByRuc(clientDocNumber);
				
				// if not Found, search in Sunat
				if (null == rucDao) {
					return "0";
				}
				
				rucDao.setCorreoElectronico(clientEmailAddress);
				rucsRepository.save(rucDao);
				return "1";
			}
		}
		
		return "0";
		
	}
	
	public String saveBonusNumber(InvoiceVo invoiceVo) {
		
		if (!invoiceVo.getClientDocNumber().equals(ApplicationService.CLIENTES_VARIOS_DOC_NUMBER)) {
			if (invoiceVo.getClientDocType().equals(ApplicationService.DNI)) {
				
				// search DNI in DB
				DNIDao dniDao = dnisRepository.findFirstByDni(invoiceVo.getClientDocNumber());
				
				// if not Found, search in Sunat
				if (null == dniDao) {
					return "0";
				}
				
				if (!invoiceVo.getBonusNumber().equals(dniDao.getBonusNumber())) {
					dniDao.setBonusNumber(invoiceVo.getBonusNumber());
					dnisRepository.save(dniDao);
				}
				
				return "1";
			} else if (invoiceVo.getClientDocType().equals(ApplicationService.RUC)) {
	
				RUCDao rucDao = rucsRepository.findFirstByRuc(invoiceVo.getClientDocNumber());
				
				// if not Found, search in Sunat
				if (null == rucDao) {
					return "0";
				}
				
				if (!invoiceVo.getBonusNumber().equals(rucDao.getBonusNumber())) {
					rucDao.setBonusNumber(invoiceVo.getBonusNumber());
					rucsRepository.save(rucDao);
				}
				return "1";
			}
		}
		
		return "0";
		
	}
	
	public String saveClientAddressForDNIOrRUC(InvoiceVo invoiceVo) {
		
		if (!invoiceVo.getClientDocNumber().equals(ApplicationService.CLIENTES_VARIOS_DOC_NUMBER)) {
			if (invoiceVo.getClientDocType().equals(ApplicationService.DNI)) {
				
				// search DNI in DB
				DNIDao dniDao = dnisRepository.findFirstByDni(invoiceVo.getClientDocNumber());
				
				// if not Found, search in Sunat
				if (null == dniDao) {
					return "0";
				}
				
				if (!invoiceVo.getClientAddress().trim().equals(dniDao.getDireccion())) {
					dniDao.setDireccion(invoiceVo.getClientAddress().trim());
					dnisRepository.save(dniDao);
				}
				
				return "1";
			} else if (invoiceVo.getClientDocType().equals(ApplicationService.RUC)) {
				
				RUCDao rucDao = rucsRepository.findFirstByRuc(invoiceVo.getClientDocNumber());
				
				// if not Found, search in Sunat
				if (null == rucDao) {
					return "0";
				}
				
				if (!invoiceVo.getClientAddress().trim().equals(rucDao.getDireccion())) {
					rucDao.setDireccion(invoiceVo.getClientAddress().trim());
					rucsRepository.save(rucDao);
				}
				return "1";
			}
		} 
		
		return "0";
		
	}
	
	public void deletePath(String path) {
		// Delete files in xmlsSunat folder
		File f = new File(path);
		if (f.exists() && f.isDirectory()) {
			try {
				FileUtils.cleanDirectory(f.getAbsoluteFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getBasePath() {
		if (null == this.basePath) {
			try {
				this.basePath = resourceLoader.getResource("classpath:/static/").getFile().getPath();
				
				return this.basePath;
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} 
		
		return basePath;
	}
	
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public double toFixedTwo(double amt) {
		return Math.floor(amt * 100) / 100.0;
	}
	
	public double roundTwo(double amt) {
		return Math.round(amt * 100) / 100.0;
	}
	
	public static Date getDateAtMidnightNDaysAgo(Integer numberOfDaysAgo, String timeZoneID) {
		Calendar date = getCurrentCalendarAtMidnight(timeZoneID);
		date.add(Calendar.DAY_OF_MONTH, -numberOfDaysAgo);

		return date.getTime();
	}
	
	public static Calendar getCurrentCalendarAtMidnight(String timeZoneID) {
		Calendar calendar = GregorianCalendar.getInstance();
		Date localDate = transformGMTDateToZone(new Date(System.currentTimeMillis()), timeZoneID);// + 4 * 3600 * 1000
		calendar.setTime(localDate);
		// reset hour, minutes, seconds and millis
		calendar.set(Calendar.HOUR_OF_DAY, -TimeZone.getTimeZone(timeZoneID).getOffset(localDate.getTime())/3600000);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar;
	}
	
	public static Date getTodayDateAtMidnight(String timeZoneId) {
		return getDateAtMidnightNDaysAgo(0, timeZoneId);
	}
	
	public static Date getDateAtMidnightByDayOfYear(Integer dayOfTheYear, String timeZoneId) {
		Calendar date = getCurrentCalendarAtMidnight(timeZoneId);
		date.set(Calendar.DAY_OF_YEAR, dayOfTheYear);

		return date.getTime();
	}
	
	public static Date getDateAtMidnightByDayOfYearByYearsAgo(Integer dayOfTheYear, String timeZoneId, Integer numberOfYearsAgo) {
		Calendar date = getCurrentCalendarAtMidnight(timeZoneId);
		date.set(Calendar.DAY_OF_YEAR, dayOfTheYear);
		date.add(Calendar.YEAR, -1 * numberOfYearsAgo);
		
		return date.getTime();
	}
	
	public static Date getDateBeginningOfMonth(Integer month_number, String timeZoneId) {
		Calendar tempDate = getCurrentCalendarAtMidnight(timeZoneId);
		tempDate.set(Calendar.MONTH, month_number);
		tempDate.set(Calendar.DAY_OF_MONTH, 1);
		
		return tempDate.getTime();
	}
	
	public static Date getDateBeginningOfNextMonth(Integer month_number, String timeZoneId) {
		Calendar tempDate = getCurrentCalendarAtMidnight(timeZoneId);
		tempDate.set(Calendar.MONTH, month_number + 1);
		tempDate.set(Calendar.DAY_OF_MONTH, 1);
		
		return tempDate.getTime();
	}
	
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static Date addNDaysToDate(Date date, Integer nbrOfDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, nbrOfDays);
		
		return calendar.getTime();
	}
	
	public static String formatDate(Date date, String isoDatePattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);

		return simpleDateFormat.format(date);
	}
	
    public static Date transformGMTDateToZone(Date date, String timeZoneID) {
		TimeZone tz = TimeZone.getTimeZone(timeZoneID);
		return new Date(date.getTime() + tz.getOffset(date.getTime()));
	}
    
    public static Date transformZoneToGMTDate(Date date, String timeZoneID) {
		TimeZone tz = TimeZone.getTimeZone(timeZoneID);
		return new Date(date.getTime() - tz.getOffset(date.getTime()));
	}
    
    public static void populateCompanyDetailsVo(InvoiceVo invoiceVo, GlobalProperties globalProperties) {
		invoiceVo.setMyRuc(globalProperties.getMyRuc());
		invoiceVo.setCompanyName(globalProperties.getCompanyName());
		invoiceVo.setCompanyAddress(globalProperties.getCompanyAddress());
		invoiceVo.setCompanyState(globalProperties.getCompanyState());
		invoiceVo.setCompanyProvince(globalProperties.getCompanyProvince());
		invoiceVo.setCompanyDistrict(globalProperties.getCompanyDistrict());
		invoiceVo.setCompanyCountryCode(globalProperties.getCompanyCountryCode());
		invoiceVo.setCompanyUbigeo(globalProperties.getCompanyUbigeo());
		invoiceVo.setCompanyAddress2(globalProperties.getCompanyAddress2());
		invoiceVo.setCompanyPhoneNumber(globalProperties.getCompanyPhoneNumber());
		invoiceVo.setCompanyUrl(globalProperties.getCompanyUrl());
		
	}
    
    public static void populateCompanyDetailsDao(InvoiceDao invoiceDao, GlobalProperties globalProperties) {
    	invoiceDao.setMyRuc(globalProperties.getMyRuc());
    	invoiceDao.setCompanyName(globalProperties.getCompanyName());
    	invoiceDao.setCompanyAddress(globalProperties.getCompanyAddress());
    	invoiceDao.setCompanyState(globalProperties.getCompanyState());
    	invoiceDao.setCompanyProvince(globalProperties.getCompanyProvince());
    	invoiceDao.setCompanyDistrict(globalProperties.getCompanyDistrict());
    	invoiceDao.setCompanyCountryCode(globalProperties.getCompanyCountryCode());
    	invoiceDao.setCompanyUbigeo(globalProperties.getCompanyUbigeo());
    	invoiceDao.setCompanyAddress2(globalProperties.getCompanyAddress2());
    	invoiceDao.setCompanyPhoneNumber(globalProperties.getCompanyPhoneNumber());
    	invoiceDao.setCompanyUrl(globalProperties.getCompanyUrl());
	}
	
}
