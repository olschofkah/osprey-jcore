package com.osprey.marketdata.feed;

import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuoteContainer;

public interface IUltraSecurityQuoteService {

	public SecurityQuoteContainer quoteUltra(SecurityKey s)
			throws MarketDataNotAvailableException, MarketDataIOException;

	public SecurityQuoteContainer quoteUltra(Security s) throws MarketDataNotAvailableException, MarketDataIOException;

	public SecurityQuoteContainer quoteUltra(SecurityQuoteContainer sqc)
			throws MarketDataNotAvailableException, MarketDataIOException;
}
