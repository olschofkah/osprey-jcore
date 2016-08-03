package com.osprey.marketdata.batch.tasklet;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;

import com.osprey.marketdata.service.MarketDataLoadDateService;
import com.osprey.securitymaster.constants.OspreyConstants;

public class MarketDataLoadDecider implements JobExecutionDecider {

	final static Logger logger = LogManager.getLogger(MarketDataLoadDecider.class);

	@Autowired
	private MarketDataLoadDateService dtService;

	public static final String DO_FETCH = "DO_FETCH";
	public static final String DO_LOAD = "DO_LOAD";

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

		ZonedDateTime lastLoadDate = dtService.findLastLoadDate();

		logger.info("Last load date for market data is {} ", () -> lastLoadDate);

		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime cutoff = ZonedDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 21, 0, 0, 0,
				ZoneId.of(OspreyConstants.ZONED_DATE_TIME_ZONE_ID_NY));

		if (lastLoadDate.plusDays(1).isBefore(cutoff)) {
			return new FlowExecutionStatus(DO_FETCH);
		}
		return new FlowExecutionStatus(DO_LOAD);
	}

}
