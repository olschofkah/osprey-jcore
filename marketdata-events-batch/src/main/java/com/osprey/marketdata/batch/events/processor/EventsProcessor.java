package com.osprey.marketdata.batch.events.processor;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.marketdata.feed.ychart.YChartHistoricalEventsClient;
import com.osprey.math.EarningsCalculator;
import com.osprey.securitymaster.FundamentalQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class EventsProcessor implements ItemProcessor<SecurityQuoteContainer, SecurityQuoteContainer> {

	private final static Logger logger = LogManager.getLogger(EventsProcessor.class);

	private AtomicLong throttleCapacity;

	private YChartHistoricalEventsClient yChartClient;

	public EventsProcessor(YChartHistoricalEventsClient yChartClient, AtomicLong throttleCapacity) {
		this.yChartClient = yChartClient;
		this.throttleCapacity = throttleCapacity;
	}

	@Override
	public SecurityQuoteContainer process(SecurityQuoteContainer sqc) throws Exception {

		logger.info("Findings Events for {} ", () -> sqc.getKey().getSymbol());

		checkThrottle();

		try {
			yChartClient.populateEvents(sqc);
		} catch (MarketDataNotAvailableException e) {
			logger.warn("Failed to find events for {} | Message {}", new Object[] { sqc.getKey().getSymbol(), e.getMessage() });
			return null;
		}

		sqc.sortEventsDescending();

		populateCalcs(sqc);

		return sqc;
	}

	private void populateCalcs(SecurityQuoteContainer sqc) {

		FundamentalQuote fundamentalQuote = sqc.getFundamentalQuote();

		double earningsMove = EarningsCalculator.calcEarningsPercentMove(sqc, 10, 4);
		if (earningsMove > 0) {
			fundamentalQuote.setEarningsAveragePercent(earningsMove);
		}

		double earningsVolatility = EarningsCalculator.calcEarningsVolatility(sqc, 10, 4);
		if (earningsVolatility > 0) {
			fundamentalQuote.setEarningsVolatility(earningsVolatility);
		}

	}

	private void checkThrottle() throws InterruptedException {
		while (throttleCapacity.get() <= 0) {
			logger.trace("Waiting for capacity ...");
			Thread.sleep(5);
		}
		throttleCapacity.getAndDecrement();
	}

}
