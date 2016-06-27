package com.osprey.marketdata.batch.writer;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.osprey.integration.slack.SlackClient;
import com.osprey.marketdata.batch.processor.InitialScreenProcessor;
import com.osprey.screen.ScreenSuccessSecurity;

public class SlackOutputWriter implements ItemWriter<ScreenSuccessSecurity> {
	final static Logger logger = LogManager.getLogger(InitialScreenProcessor.class);

	@Autowired
	private SlackClient slack;

	@Override
	public void write(List<? extends ScreenSuccessSecurity> items) throws Exception {
		logger.info("Alerting slack to the hot list items {}", () -> items);
	}

}
