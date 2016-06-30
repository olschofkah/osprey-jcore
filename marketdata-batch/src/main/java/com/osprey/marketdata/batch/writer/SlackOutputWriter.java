package com.osprey.marketdata.batch.writer;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.osprey.integration.slack.SlackClient;
import com.osprey.screen.ScreenSuccessSecurity;

public class SlackOutputWriter implements ItemWriter<ScreenSuccessSecurity> {
	final static Logger logger = LogManager.getLogger(SlackOutputWriter.class);

	@Autowired
	private SlackClient slack;

	@Override
	public void write(List<? extends ScreenSuccessSecurity> items) throws Exception {
		logger.info("Alerting slack to the hot list items {}", () -> items);
		if (!items.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ScreenSuccessSecurity sss : items) {
				sb.append(sss.toString());
			}
			slack.postMessage(sb.toString());
		}
	}

}
