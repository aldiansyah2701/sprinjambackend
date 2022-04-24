package com.aldidb.backenddb.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WorkerSchedulerService {
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger("SERVICE_APPENDER");
	
	@Autowired
	private PostingTransactionService postingTransactionService;
	
	@Scheduled(cron="${schedule.cron}", zone="Asia/Jakarta") //setiap pukul set 16 sore , setiap hari 
	public void readSchedulerTransfer() {
		LOGGER.info("scheduler run");
		postingTransactionService.invoiceChekProcess();
	}

}
