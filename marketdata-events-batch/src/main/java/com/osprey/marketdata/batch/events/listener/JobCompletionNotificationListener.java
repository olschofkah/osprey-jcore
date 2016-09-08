package com.osprey.marketdata.batch.events.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import com.osprey.integration.slack.SlackClient;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private final static Logger logger = LogManager.getLogger(JobCompletionNotificationListener.class);

	private SlackClient slack;

	public JobCompletionNotificationListener(SlackClient slack) {
		this.slack = slack;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		slack.postMessage("Starting the security events master rebuild master ...");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info("Work Complete");
			slack.postMessage("The events job is done master.");
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			logger.info("Work Failed");
			slack.postMessage("The job failed master...  What would you like me to do?");
		}

		// ugly but works ...
		System.exit(0);
	}
}
