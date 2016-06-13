package com.osprey.marketdata.rest.endpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osprey.marketdata.feed.ILiveSecurityQuoteService;
import com.osprey.marketdata.feed.mock.MockMarketDataFeedService;
import com.osprey.securitymaster.PricedSecurity;
import com.osprey.securitymaster.Security;

@RestController
public class RestQuoteService {

	final static Logger logger = LogManager.getLogger(MockMarketDataFeedService.class);

	@Autowired
	private ILiveSecurityQuoteService liveQuoteService;

	@RequestMapping("/quote/{symbol}")
	public PricedSecurity quote(@PathVariable(value = "symbol") String symbol) {

		logger.info("/quote/{}/ called ", () -> symbol);

		Security security = new Security(symbol);

		PricedSecurity quote = liveQuoteService.quote(security);

		logger.info("/quote/{}/ priced @ {} ", () -> symbol, () -> quote);

		return quote;
	}

}
