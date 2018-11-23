package hello.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hello.Application;
import hello.services.Utils;

@Component
public class ScheduledTasks {
	private static Logger logger = LogManager.getLogger(Application.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private String basePath;
	
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private Utils utils;
	
	@Scheduled(cron = "0 0 2 * * *")
	public void reportCurrentTime() {
		logger.info("Cron job is starting at " + dateFormat.format(new Date()));
		
		// clean up jasterreports folder
		utils.deletePath(getBasePath() + "/jasperReports");
		
		// clean up xmlsSunat folder
		utils.deletePath(getBasePath() + "/xmlsSunat");
		
		logger.info("Cron job is ending at " + dateFormat.format(new Date()));
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
