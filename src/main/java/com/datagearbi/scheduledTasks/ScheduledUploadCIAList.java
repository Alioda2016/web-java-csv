package com.datagearbi.scheduledTasks;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.datagearbi.service.UploaderCIAListTemplate;

@Component
public class ScheduledUploadCIAList {
	
	@Autowired
	private UploaderCIAListTemplate uploaderCIAListTemplate;
	private static final Logger logger = LoggerFactory.getLogger(ScheduledUploadCIAList.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@Scheduled(cron = "${cron.cia.expression}")
	public void uploadingProcess() throws Exception {
	

		logger.info("start uploading process  scheduled Task=>", dateTimeFormatter.format(LocalDateTime.now()));
		this.uploaderCIAListTemplate.uploadFile();
		logger.info("start uploading process scheduled Task");
	}
}
