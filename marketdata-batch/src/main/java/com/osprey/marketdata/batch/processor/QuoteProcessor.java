package com.osprey.marketdata.batch.processor;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.osprey.marketdata.feed.IFundamentalSecurityQuoteService;
import com.osprey.marketdata.feed.IHistoricalSecurityQuoteSerice;
import com.osprey.math.OspreyQuantMath;
import com.osprey.securitymaster.ExtendedFundamentalPricedSecurityWithHistory;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.constants.OspreyConstants;

public class QuoteProcessor implements ItemProcessor<Security, ExtendedFundamentalPricedSecurityWithHistory> {

	final static Logger logger = LogManager.getLogger(QuoteProcessor.class);

	@Autowired
	private IFundamentalSecurityQuoteService fundamentalQuoteService;
	@Autowired
	private IHistoricalSecurityQuoteSerice historicalQuoteService;

	@Override
	public ExtendedFundamentalPricedSecurityWithHistory process(Security item) throws Exception {

		logger.debug("Quoting initial screen on {} ", () -> item.getTicker());

		FundamentalPricedSecurity quote = fundamentalQuoteService.quoteFundamental(item);

		LocalDate today = LocalDate.now();
		LocalDate overOneYearAgo = today.minusYears(1).minusWeeks(1);

		List<HistoricalSecurity> historicals = historicalQuoteService.fetchHistorical(item, today, overOneYearAgo);

		ExtendedFundamentalPricedSecurityWithHistory extendedQuote = new ExtendedFundamentalPricedSecurityWithHistory(
				quote);
		extendedQuote.setHistory(historicals);

		decorateQuoteWithCustomCalculations(extendedQuote);

		return extendedQuote;
	}

	private void decorateQuoteWithCustomCalculations(ExtendedFundamentalPricedSecurityWithHistory extendedQuote) {

		double volatility = OspreyQuantMath.volatility(OspreyConstants.MARKET_DAYS_IN_YEAR, extendedQuote.getHistory());
		
		double sma12 = OspreyQuantMath.sma(12, extendedQuote.getHistory());
		double ema12 = OspreyQuantMath.ema(sma12, 12, extendedQuote.getHistory());
		
		// TODO Determine & Add more calculations.
		
		extendedQuote.set_oVolatility(volatility);
		extendedQuote.set_12ema(ema12);
		extendedQuote.set_12sma(sma12);

	}

}
