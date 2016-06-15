package com.osprey.marketdata.batch.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	final static Logger logger = LogManager.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info("Work Complete");

			// TODO ... do stuff in the listener 
//			List<Person> results = jdbcTemplate.query("SELECT first_name, last_name FROM people",
//					new RowMapper<Person>() {
//						@Override
//						public Person mapRow(ResultSet rs, int row) throws SQLException {
//							return new Person(rs.getString(1), rs.getString(2));
//						}
//					});
//
//			for (Person person : results) {
//				log.info("Found <" + person + "> in the database.");
//			}

		}
	}
}
