package com.osprey.marketdata.feed.yahoo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.osprey.marketdata.feed.IUltraSecurityQuoteService;
import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.marketdata.feed.yahoo.pojo.Result;
import com.osprey.marketdata.feed.yahoo.pojo.YahooQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class YahooQuoteClient implements IUltraSecurityQuoteService {

	final static Logger logger = LogManager.getLogger(YahooQuoteClient.class);

	@Autowired
	private ApplicationContext appCtx;

	@Autowired
	private RestTemplate http;

	@Override
	public SecurityQuoteContainer quoteUltra(SecurityKey s)
			throws MarketDataNotAvailableException, MarketDataIOException {
		return quoteUltra(new SecurityQuoteContainer(s), false);

	}

	@Override
	public SecurityQuoteContainer quoteUltra(Security s) throws MarketDataNotAvailableException, MarketDataIOException {
		SecurityQuoteContainer sqc = new SecurityQuoteContainer(s.getKey());
		sqc.setSecurity(s);
		return quoteUltra(sqc, false);
	}

	@Override
	public SecurityQuoteContainer quoteUltra(SecurityQuoteContainer sqc, boolean simplerQuote)
			throws MarketDataNotAvailableException, MarketDataIOException {
		logger.info("Quoting for {} ... ", () -> sqc.getKey().getSymbol());

		YahooQuoteUrlBuilder yahooQuoteUrlBuilder = appCtx.getBean(YahooQuoteUrlBuilder.class,
				sqc.getKey().getSymbol());

		String url;
		
		if(simplerQuote) {
			url = yahooQuoteUrlBuilder
					.summaryDetail()
					.summaryProfile() 
					.calendarEvents()
					.defaultKeyStatistics()
					.earnings()
					.financialData()
					.price()
					.build();
		} else {
			url = yahooQuoteUrlBuilder
					.summaryDetail()
					.summaryProfile() 
					.calendarEvents()
					.defaultKeyStatistics()
					.earnings()
					.financialData()
					.price()
					.netSharePurchaseActivity()
					.majorHoldersBreakdown()
					.build();
		}
		
		YahooQuote yahooQuote = null;
		try {
			yahooQuote = http.getForObject(url, YahooQuote.class);
		} catch (HttpClientErrorException e1) {
			if (e1.getMessage().contains("404 Not Found")) {
				throw new MarketDataNotAvailableException(
						"404 when quoting " + sqc.getKey().getSymbol() + " Message: " + e1.getMessage());
			} else {
				throw new MarketDataIOException(e1);
			}
		} catch (HttpMessageNotReadableException | HttpServerErrorException | ResourceAccessException e2) {
			throw new MarketDataIOException(e2);
		}

		if (yahooQuote == null || yahooQuote.getQuoteSummary() == null
				|| yahooQuote.getQuoteSummary().getResult() == null) {
			logger.error("Failed quoting {} ", new Object[] { sqc.getKey().getSymbol() });

			// try again requesting less data. 
			if (!simplerQuote) {
				return quoteUltra(sqc, true);
			}

			throw new MarketDataIOException("Failed Quoting " + sqc.getKey().getSymbol());
		}

		Result result = yahooQuote.getQuoteSummary().getResult().get(0);

		// TODO Strip out fmt and longFmt from generated objects
		// TODO Strip out additionalProperties map from generated objects
		// TODO Add Quote Sanity Checks

		logger.debug("Completed quoting {} ... ", () -> sqc.getKey().getSymbol());

		return YahooQuoteResultMapper.map(result, sqc);
	}

}
