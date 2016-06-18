package com.osprey.marketdata.feed.yahoo;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import com.osprey.marketdata.feed.IFundamentalSecurityQuoteService;
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
	public FundamentalPricedSecurity quoteFundamental(Security s) {

		logger.debug("Quoting for {} ... ", () -> s.getSymbol());

		YahooQuoteUrlBuilder yahooQuoteUrlBuilder = appCtx.getBean(YahooQuoteUrlBuilder.class, s.getSymbol());

		String url = yahooQuoteUrlBuilder
				.summaryDetail()
				.summaryProfile()
				.calendarEvents()
				.defaultKeyStatistics()
				.earnings()
				.financialData()
				.build();

		YahooQuote yahooQuote = http.getForObject(url, YahooQuote.class);

		Result result = yahooQuote.getQuoteSummary().getResult().get(0);

		// TODO Add Error Handling
		// TODO Strip out fmt and longFmt from generated objects
		// TODO Strip out additionalProperties map from generated objects
		
		logger.info("Quoted {} @ {} - {}", new Object[] { s.getSymbol(), result.getSummaryDetail().getBid().getRaw(),
				result.getSummaryDetail().getAsk().getRaw() });

		return mapResultToFundamentalSecurity(result, s.getSymbol());
	}

	private FundamentalPricedSecurity mapResultToFundamentalSecurity(Result result, String symbol) {
		// TODO Write more code
		return null;
	}

	@Override
	public Map<Security, FundamentalPricedSecurity> quoteFundamentalBatch(Set<Security> s) {
		throw new NotImplementedException(
				"quoteFundamentalBatch(Set<Security>) is not implemented for YahooQuoteClient");
	}

}
