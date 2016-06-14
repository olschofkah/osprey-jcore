package com.osprey.marketdata.feed;

import java.util.Map;
import java.util.Set;

import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.Security;

public interface IFundamentalSecurityQuoteService {

	public FundamentalPricedSecurity quoteFundamental(Security s);

	public Map<Security, FundamentalPricedSecurity> quoteFundamentalBatch(Set<Security> s);
}
