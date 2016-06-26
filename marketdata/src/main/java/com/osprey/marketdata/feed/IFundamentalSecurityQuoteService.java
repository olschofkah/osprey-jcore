package com.osprey.marketdata.feed;

import java.util.Map;
import java.util.Set;

import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.securitymaster.FundamentalQuote;
import com.osprey.securitymaster.SecurityKey;

public interface IFundamentalSecurityQuoteService {

	public FundamentalQuote quoteFundamental(SecurityKey s)
			throws MarketDataNotAvailableException, MarketDataIOException;

	public Map<SecurityKey, FundamentalQuote> quoteFundamentalBatch(Set<SecurityKey> s)
			throws MarketDataNotAvailableException;
}
