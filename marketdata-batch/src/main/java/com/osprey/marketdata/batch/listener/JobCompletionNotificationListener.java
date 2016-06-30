package com.osprey.marketdata.batch.listener;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.integration.slack.SlackClient;
import com.osprey.screen.ScreenSuccessSecurity;
import com.osprey.securitymaster.constants.OspreyConstants;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	final static Logger logger = LogManager.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbc;
	private final SlackClient slack;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate, SlackClient slack) {
		this.jdbc = jdbcTemplate;
		this.slack = slack;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		slack.postMessage("Starting the security master rebuild master ...");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info("Work Complete");

			final java.sql.Date today = new java.sql.Date(LocalDate.now()
					.atStartOfDay(ZoneId.of(OspreyConstants.ZONED_DATE_TIME_ZONE_ID_NY)).toInstant().toEpochMilli());

			// TODO extract to repository
			// TODO add index on just date
			List<String> result = jdbc.queryForList("select payload from tha_hot_shit where date = ?", String.class,
					today);

			ObjectMapper om = new ObjectMapper();
			List<ScreenSuccessSecurity> hotItems = new ArrayList<>(result.size());
			for (String o : result) {
				ScreenSuccessSecurity hotItem = null;
				try {
					hotItem = om.readValue(o, ScreenSuccessSecurity.class);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				hotItems.add(hotItem);
			}

			Map<String, List<String>> reportMap = new HashMap<>();
			for (ScreenSuccessSecurity hotItem : hotItems) {
				for (String string : hotItem.getNamedScreenSets()) {
					if (!reportMap.containsKey(string)) {
						reportMap.put(string, new ArrayList<>());
					}
					reportMap.get(string).add(hotItem.getKey().getSymbol());
				}
			}

			StringBuilder sb = new StringBuilder();
			sb.append("The job is done master.  I've prepared ");
			sb.append(hotItems.size());
			sb.append(" trades for you to watch today.");
			sb.append("\n");
			sb.append("\n");
			for (Entry<String, List<String>> entry : reportMap.entrySet()) {
				sb.append("Screen: ");
				sb.append(entry.getKey());
				sb.append("\n");
				sb.append("Symbol(s): ");
				for (String symbol : entry.getValue()) {
					sb.append(symbol);
					sb.append(", ");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.deleteCharAt(sb.length() - 1);
			}

			slack.postMessage(sb.toString());

		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			logger.info("Work Failed");
			slack.postMessage("The job has failed master.  What would you like me to do?");
		}
	}
}
