package com.osprey.marketdata.batch.processor;

import java.time.LocalDate;
import java.time.ZonedDateTime;
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
import com.osprey.marketdata.service.MarketScheduleService;
import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.securitymaster.FundamentalQuote;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class QuoteProcessor implements ItemProcessor<SecurityQuoteContainer, SecurityQuoteContainer> {

	final static Logger logger = LogManager.getLogger(QuoteProcessor.class);

	@Autowired
	@Qualifier("throttleCapacity")
	private AtomicLong throttleCapacity;

	@Autowired
	private IUltraSecurityQuoteService fundamentalQuoteService;
	@Autowired
	private IHistoricalQuoteSerice historicalQuoteService;
	@Autowired
	private MarketScheduleService marketSchedule;

	@Override
	public SecurityQuoteContainer process(SecurityQuoteContainer sqc) throws Exception {

		logger.info("Quoting {} ", () -> sqc.getKey().getSymbol());

		checkThrottle();

		try {
			fundamentalQuoteService.quoteUltra(sqc, false);
		} catch (MarketDataNotAvailableException e) {
			logger.warn("Failed to quote {} | Message {}", new Object[] { sqc.getKey().getSymbol(), e.getMessage() });
			return null;
		}

		LocalDate today = LocalDate.now();
		LocalDate overOneYearAgo = today.minusYears(3);

		checkThrottle();

		List<HistoricalQuote> historicals = historicalQuoteService.quoteHistorical(sqc.getKey(), overOneYearAgo, today,
				QuoteDataFrequency.DAY);
		sqc.setHistoricalQuotes(historicals);

		populateCurrentQuoteInHist(sqc);
		populateCalcs(sqc);

		sqc.sortEventsDescending();

		return sqc;
	}

	private void populateCurrentQuoteInHist(SecurityQuoteContainer sqc) {

		if (!sqc.getHistoricalQuotes().isEmpty()) {

			LocalDate firstHistDate = sqc.getHistoricalQuotes().get(0).getHistoricalDate();

			LocalDate today = LocalDate.now(); 
			if (firstHistDate.isBefore(today) && marketSchedule.hasUsEquityMarketsOpenedToday()) {

				SecurityQuote quote = sqc.getSecurityQuote();

				HistoricalQuote hq = new HistoricalQuote(sqc.getKey().getSymbol(), today);
				hq.setAdjClose(quote.getLast());
				hq.setClose(quote.getLast());
				hq.setHigh(quote.getHigh());
				hq.setLow(quote.getLow());
				hq.setOpen(quote.getOpen());
				hq.setTimestamp(ZonedDateTime.now());
				hq.setVolume(quote.getVolume());

				// insert the generated hist quote as the first quote.
				sqc.getHistoricalQuotes().add(0, hq);
			}
		}

	}

	private void populateCalcs(SecurityQuoteContainer sqc) {

		FundamentalQuote fundamentalQuote = sqc.getFundamentalQuote();

		// Calculate vol
		int volPeriod = sqc.getHistoricalQuotes().size() < 252 ? sqc.getHistoricalQuotes().size() : 252;
		if (volPeriod > 2) {
			double volatility = OspreyQuantMath.volatility(volPeriod, sqc.getHistoricalQuotes());
			fundamentalQuote.setVolatility(volatility);
		}

		try {
			double rotationIndicator = OspreyQuantMath.rotationIndicator(60, 20, 0, 1, sqc.getHistoricalQuotes());
			fundamentalQuote.setRotationIndicator(rotationIndicator);
		} catch (InsufficientHistoryException e) { // eat it
		}

		try {
			double _8Ema = OspreyQuantMath.ema(8, 0, sqc.getHistoricalQuotes());
			fundamentalQuote.set_8DayEma(_8Ema);
		} catch (InsufficientHistoryException e) { // eat it
		}

		try {
			double _10Ema = OspreyQuantMath.ema(10, 0, sqc.getHistoricalQuotes());
			fundamentalQuote.set_10DayEma(_10Ema);
		} catch (InsufficientHistoryException e) { // eat it
		}

		try {
			double _15Ema = OspreyQuantMath.ema(15, 0, sqc.getHistoricalQuotes());
			fundamentalQuote.set_15DayEma(_15Ema);
		} catch (InsufficientHistoryException e) { // eat it
		}

		try {
			double _20Ema = OspreyQuantMath.ema(20, 0, sqc.getHistoricalQuotes());
			fundamentalQuote.set_20DayEma(_20Ema);
		} catch (InsufficientHistoryException e) { // eat it
		}

		try {
			double _50Ema = OspreyQuantMath.ema(50, 0, sqc.getHistoricalQuotes());
			fundamentalQuote.set_50DayEma(_50Ema);
		} catch (InsufficientHistoryException e) { // eat it
		}

		try {
			double _100Ema = OspreyQuantMath.ema(100, 0, sqc.getHistoricalQuotes());
			fundamentalQuote.set_100DayEma(_100Ema);
		} catch (InsufficientHistoryException e) { // eat it
		}

		try {
			double _200Ema = OspreyQuantMath.ema(200, 0, sqc.getHistoricalQuotes());
			fundamentalQuote.set_200DayEma(_200Ema);
		} catch (InsufficientHistoryException e) { // eat it
		}

	}

	private void checkThrottle() throws InterruptedException {
		while (throttleCapacity.get() <= 0) {
			logger.trace("Waiting for capacity ...");
			Thread.sleep(5); // TODO make config
		}
		throttleCapacity.getAndDecrement();
	}

}
