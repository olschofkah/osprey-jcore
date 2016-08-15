package com.osprey.marketdata.batch.tasklet;

import java.time.ZonedDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;

import com.osprey.marketdata.service.MarketDataLoadDateService;
import com.osprey.marketdata.service.MarketScheduleService;

public class MarketDataLoadDecider implements JobExecutionDecider {

	final static Logger logger = LogManager.getLogger(MarketDataLoadDecider.class);

	@Autowired
	private MarketDataLoadDateService dtService;
	@Autowired
	private MarketScheduleService marketSchedule;

	public static final String DO_FETCH = "DO_FETCH";
	public static final String DO_LOAD = "DO_LOAD";

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

		ZonedDateTime lastLoadDate = dtService.findLastLoadDate();

		logger.info("Last load date for market data is {} ", () -> lastLoadDate);

		if (lastLoadDate.plusHours(3).isBefore(ZonedDateTime.now()) || marketSchedule.isUsEquityMarketsOpen()) {
			return new FlowExecutionStatus(DO_FETCH);
		}
		return new FlowExecutionStatus(DO_LOAD);
	}

}
