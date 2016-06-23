package com.osprey.marketdata.batch.processor;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.osprey.marketdata.feed.IFundamentalSecurityQuoteService;
import com.osprey.marketdata.feed.IHistoricalQuoteSerice;
import com.osprey.marketdata.feed.constants.QuoteDataFrequency;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.securitymaster.ExtendedFundamentalPricedSecurityWithHistory;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.constants.OspreyConstants;

public class QuoteProcessor implements ItemProcessor<Security, ExtendedFundamentalPricedSecurityWithHistory> {

	final static Logger logger = LogManager.getLogger(QuoteProcessor.class);

	@Autowired
	@Qualifier("throttleCapacity")
	private AtomicLong throttleCapacity;

	@Autowired
	private IFundamentalSecurityQuoteService fundamentalQuoteService;
	@Autowired
	private IHistoricalQuoteSerice historicalQuoteService;

	@Override
	public ExtendedFundamentalPricedSecurityWithHistory process(Security item) throws Exception {

		logger.debug("Quoting initial screen on {} ", () -> item.getSymbol());

		checkThrottle();

		FundamentalPricedSecurity quote = null;
		try {
			quote = fundamentalQuoteService.quoteFundamental(item);
		} catch (MarketDataNotAvailableException e) {
			logger.warn("Failed to quote {} | Message {}", new Object[] { item.getSymbol(), e.getMessage() });
			return null;
		}

		LocalDate today = LocalDate.now();
		LocalDate overOneYearAgo = today.minusYears(1).minusWeeks(1);

		checkThrottle();

		List<HistoricalQuote> historicals = historicalQuoteService.quoteHistorical(item, overOneYearAgo, today,
				QuoteDataFrequency.DAY);

		ExtendedFundamentalPricedSecurityWithHistory extendedQuote = new ExtendedFundamentalPricedSecurityWithHistory(
				quote);
		extendedQuote.setHistory(historicals);

		decorateQuoteWithCustomCalculations(extendedQuote);

		return extendedQuote;
	}

	private void checkThrottle() throws InterruptedException {
		while (throttleCapacity.get() <= 0) {
			logger.trace("Waiting for capacity ...");
			Thread.sleep(5); // TODO make config
		}
		throttleCapacity.getAndDecrement();
	}

	private void decorateQuoteWithCustomCalculations(ExtendedFundamentalPricedSecurityWithHistory extendedQuote) {

		try {
			double volatility = OspreyQuantMath.volatility(
					Math.min(extendedQuote.getHistory().size(), OspreyConstants.MARKET_DAYS_IN_YEAR),
					extendedQuote.getHistory());

			double sma12 = OspreyQuantMath.sma(12, extendedQuote.getHistory());
			double ema12 = OspreyQuantMath.ema(sma12, 12, extendedQuote.getHistory());

			// TODO Determine & Add more calculations.

			extendedQuote.set_oVolatility(volatility);
			extendedQuote.set_12ema(ema12);
			extendedQuote.set_12sma(sma12);

		} catch (InsufficientHistoryException e) {
			logger.error("Insufficient historical prices to calculate stats on {}, size avaialble {} ",
					new Object[] { extendedQuote.getSymbol(), extendedQuote.getHistory().size() });
		}
	}

}
