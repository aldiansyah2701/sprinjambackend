package com.aldidb.backenddb.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WorkerSchedulerService {
	
	@Autowired
	private PostingTransactionService postingTransactionService;
	
	@Scheduled(cron="${schedule.cron}", zone="Asia/Jakarta") //setiap pukul set 5 pagi, setiap hari 
	public void readSchedulerTransfer() {

		System.out.println("TEST TRANSFER SCHEDULER");
		postingTransactionService.invoiceChekProcess();
	}

}
