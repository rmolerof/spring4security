package corp.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import corp.Application;
import corp.model.SubmitInvoiceGroupCriteria;
import corp.services.ApplicationService;
import corp.services.GlobalProperties;
import corp.services.Utils;

@Component
public class ScheduledTasks {
	private static Logger logger = LogManager.getLogger(Application.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private String basePath;
	
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private Utils utils;
	@Autowired
	private GlobalProperties globalProperties;
	@Autowired
	ApplicationService applicationService;
	
	// Wednesday Local Time (GMT-5 Lima Peru)
	@Scheduled(cron = "0 30 8 * * WED") 
	public void submitSunat() {
		Date localDate = Utils.transformGMTDateToZone(new Date(), globalProperties.getTimeZoneID());
		
		logger.info("Cron job: submitBonus is starting at " + dateFormat.format(localDate));
		
		applicationService.submitInvoicesToSunat(SubmitInvoiceGroupCriteria.NORMAL, Utils.getDateAtMidnightNDaysAgo(3, globalProperties.getTimeZoneID()));
				
		logger.info("Cron job: submitBonus is ending at " + dateFormat.format(localDate));
	}
	
	// 8 am => 3am GMT-5 Lima Peru 
	@Scheduled(cron = "0 0 8 * * *") 
	public void submitBonus() {
		Date localDate = Utils.transformGMTDateToZone(new Date(), globalProperties.getTimeZoneID());
		
		logger.info("Cron job: submitBonus is starting at " + dateFormat.format(localDate));
		
		applicationService.submitInvoicesToBonus(SubmitInvoiceGroupCriteria.NORMAL, Utils.getDateAtMidnightNDaysAgo(1, globalProperties.getTimeZoneID()));
				
		logger.info("Cron job: submitBonus is ending at " + dateFormat.format(localDate));
	}
	
	// 7 am => 2am GMT-5 Lima Peru 
	@Scheduled(cron = "0 0 7 * * *") 
	public void cleanUpJasperFilesAndSunatXmls() {
		logger.info("Cron job: cleanUpJasperFilesAndSunatXmls is starting at " + dateFormat.format(Utils.transformGMTDateToZone(new Date(), globalProperties.getTimeZoneID())));
		
		// clean up jasterreports folder
		utils.deletePath(getBasePath() + "/jasperReports");
		
		// clean up xmlsSunat folder
		utils.deletePath(getBasePath() + "/xmlsSunat");
		
		logger.info("Cron job: cleanUpJasperFilesAndSunatXmls is ending at " + dateFormat.format(Utils.transformGMTDateToZone(new Date(), globalProperties.getTimeZoneID())));
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
}
