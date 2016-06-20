package com.osprey.marketdata.feed.yahoo;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.osprey.marketdata.feed.IFundamentalSecurityQuoteService;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.marketdata.feed.yahoo.pojo.Result;
import com.osprey.marketdata.feed.yahoo.pojo.YahooQuote;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.Security;

public class YahooQuoteClient implements IFundamentalSecurityQuoteService {

	@Autowired
	private ApplicationContext appCtx;

	final static Logger logger = LogManager.getLogger(YahooQuoteClient.class);

	@Autowired
	private RestTemplate http;

	@Override
	public FundamentalPricedSecurity quoteFundamental(Security s) throws MarketDataNotAvailableException {

		logger.info("Quoting for {} ... ", () -> s.getSymbol());

		YahooQuoteUrlBuilder yahooQuoteUrlBuilder = appCtx.getBean(YahooQuoteUrlBuilder.class, s.getSymbol());

		String url = yahooQuoteUrlBuilder
				.summaryDetail()
				// .summaryProfile() // TODO move to pulling once a week
				.calendarEvents()
				// .defaultKeyStatistics() // TODO move to pulling once a week
				// .earnings() // TODO move to pulling once a week
				.financialData()
				// .price() // TODO Do we ever need this?
				.build();

		YahooQuote yahooQuote = null;
		try {
			yahooQuote = http.getForObject(url, YahooQuote.class);
		} catch (HttpClientErrorException e1) {
			if (e1.getMessage().contains("404 Not Found")) {
				throw new MarketDataNotAvailableException(
						"404 when quoting " + s.getSymbol() + " Message: " + e1.getMessage());
			} else {
				throw new RuntimeException(e1);
			}
		}

		Result result = yahooQuote.getQuoteSummary().getResult().get(0);

		// TODO Add Error Handling
		// TODO Strip out fmt and longFmt from generated objects
		// TODO Strip out additionalProperties map from generated objects
		// TODO Add Quote Sanity Checks

		logger.debug("Completed quoting {} ... ", () -> s.getSymbol());

		return YahooQuoteResultMapper.map(result, new FundamentalPricedSecurity(s.getSymbol()));
	}

	@Override
	public Map<Security, FundamentalPricedSecurity> quoteFundamentalBatch(Set<Security> s) {
		throw new NotImplementedException(
				"quoteFundamentalBatch(Set<Security>) is not implemented for YahooQuoteClient");
	}

}
