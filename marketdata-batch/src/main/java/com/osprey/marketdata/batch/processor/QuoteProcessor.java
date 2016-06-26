package com.osprey.marketdata.batch.processor;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.osprey.marketdata.feed.IHistoricalQuoteSerice;
import com.osprey.marketdata.feed.IUltraSecurityQuoteService;
import com.osprey.marketdata.feed.constants.QuoteDataFrequency;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.securitymaster.EnhancedSecurity;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class QuoteProcessor implements ItemProcessor<SecurityQuoteContainer, SecurityQuoteContainer> {

	final static Logger logger = LogManager.getLogger(QuoteProcessor.class);

	@Autowired
	@Qualifier("throttleCapacity")
	private AtomicLong throttleCapacity;

	@Autowired
	private IUltraSecurityQuoteService fundamentalQuoteService;
	@Autowired
	private IHistoricalQuoteSerice historicalQuoteService;

	@Override
	public SecurityQuoteContainer process(SecurityQuoteContainer item) throws Exception {

		logger.debug("Quoting initial screen on {} ", () -> item.getKey().getSymbol());

		checkThrottle();

		try {
			fundamentalQuoteService.quoteUltra(item);
		} catch (MarketDataNotAvailableException e) {
			logger.warn("Failed to quote {} | Message {}", new Object[] { item.getKey().getSymbol(), e.getMessage() });
			return null;
		}

		LocalDate today = LocalDate.now();
		LocalDate overOneYearAgo = today.minusYears(1).minusWeeks(1);

		checkThrottle();

		List<HistoricalQuote> historicals = historicalQuoteService.quoteHistorical(item.getKey(), overOneYearAgo, today,
				QuoteDataFrequency.DAY);
		item.setHistoricalQuotes(historicals);

		addEnhancedQuote(item);

		return item;
	}

	private void checkThrottle() throws InterruptedException {
		while (throttleCapacity.get() <= 0) {
			logger.trace("Waiting for capacity ...");
			Thread.sleep(5); // TODO make config
		}
		throttleCapacity.getAndDecrement();
	}

	private void addEnhancedQuote(SecurityQuoteContainer qc) {

		EnhancedSecurity es = new EnhancedSecurity(qc.getKey());
		List<HistoricalQuote> historicalQuotes = qc.getHistoricalQuotes();

		try {
			double volatility = OspreyQuantMath.volatility(
					Math.min(historicalQuotes.size(), OspreyConstants.MARKET_DAYS_IN_YEAR), historicalQuotes);

			double sma12 = OspreyQuantMath.sma(12, historicalQuotes);
			double ema12 = OspreyQuantMath.ema(sma12, 12, historicalQuotes);

			// TODO Determine & Add more calculations.

			es.setAlphaForEma(1);
			es.setVolatility360(volatility);
			es.setEma12(ema12);

		} catch (InsufficientHistoryException e) {
			logger.error("Insufficient historical prices to calculate stats on {}, size avaialble {} ",
					new Object[] { qc.getKey().getSymbol(), historicalQuotes.size() });
		}
	}

}
