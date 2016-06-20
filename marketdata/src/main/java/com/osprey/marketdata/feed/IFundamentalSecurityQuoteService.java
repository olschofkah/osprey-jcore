package com.osprey.marketdata.feed;

import java.util.Map;
import java.util.Set;

import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.Security;

public interface IFundamentalSecurityQuoteService {

	public FundamentalPricedSecurity quoteFundamental(Security s) throws MarketDataNotAvailableException;

	public Map<Security, FundamentalPricedSecurity> quoteFundamentalBatch(Set<Security> s) throws MarketDataNotAvailableException;
}
