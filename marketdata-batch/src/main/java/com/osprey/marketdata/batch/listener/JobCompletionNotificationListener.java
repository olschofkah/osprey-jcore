package com.osprey.marketdata.batch.listener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import com.osprey.integration.slack.SlackClient;
import com.osprey.screen.HotListItem;
import com.osprey.screen.ModelSymbolStatistic;
import com.osprey.screen.repository.IHotItemRepository;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private final static Logger logger = LogManager.getLogger(JobCompletionNotificationListener.class);

	private IHotItemRepository repo;
	private SlackClient slack;

	public JobCompletionNotificationListener(IHotItemRepository repo, SlackClient slack) {
		this.repo = repo;
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

			List<HotListItem> hotItems = repo.findForDate(LocalDate.now());

			Map<String, List<String>> reportMap = new HashMap<>();
			for (HotListItem hotItem : hotItems) {
				for (ModelSymbolStatistic model : hotItem.getModels()) {
					if (!reportMap.containsKey(model.getModelName())) {
						reportMap.put(model.getModelName(), new ArrayList<>());
					}
					reportMap.get(model.getModelName()).add(hotItem.getKey().getSymbol());
				}
			}

			List<String> symbols;

			StringBuilder sb = new StringBuilder();
			sb.append("The job is done master.  I've prepared ");
			sb.append(hotItems.size());
			sb.append(" trades for you to watch today.");
			for (Entry<String, List<String>> entry : reportMap.entrySet()) {
				sb.append("\n");
				sb.append("\n");
				sb.append("Screen: ");
				sb.append(entry.getKey());
				sb.append("\n");
				sb.append("Symbol(s): ");

				symbols = entry.getValue();
				Collections.sort(symbols);

				for (String symbol : symbols) {
					sb.append(symbol);
					sb.append(", ");
				}

				sb.deleteCharAt(sb.length() - 1);
				sb.deleteCharAt(sb.length() - 1);
			}

			slack.postMessage(sb.toString());

		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			logger.info("Work Failed");
			slack.postMessage("Well fuck, that didn't work...  What would you like me to do?");
		}

		// ugly but works ...
		System.exit(0);
	}
}
