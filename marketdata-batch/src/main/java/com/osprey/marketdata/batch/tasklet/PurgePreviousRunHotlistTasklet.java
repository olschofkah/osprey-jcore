package com.osprey.marketdata.batch.tasklet;

import java.time.LocalDate;
import java.time.ZoneId;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import com.osprey.securitymaster.constants.OspreyConstants;

public class PurgePreviousRunHotlistTasklet implements Tasklet {

	final static Logger logger = LogManager.getLogger(PurgePreviousRunHotlistTasklet.class);

	private JdbcTemplate jdbc;

	public PurgePreviousRunHotlistTasklet(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		final java.sql.Date today = new java.sql.Date(LocalDate.now()
				.atStartOfDay(ZoneId.of(OspreyConstants.ZONED_DATE_TIME_ZONE_ID_NY)).toInstant().toEpochMilli());

		logger.info("Removing existing hotlist items for {}.", () -> today);

		// TODO extract to repo
		jdbc.update("delete from tha_hot_shit where date = ?", today);
		
		return RepeatStatus.FINISHED;
	}

}
