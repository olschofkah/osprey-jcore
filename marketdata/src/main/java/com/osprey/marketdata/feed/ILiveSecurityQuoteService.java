package com.osprey.marketdata.feed;

import java.util.Map;
import java.util.Set;

import com.osprey.securitymaster.PricedSecurity;
import com.osprey.securitymaster.Security;

public interface ILiveSecurityQuoteService {

	public PricedSecurity quote(Security s);

	public Map<Security, PricedSecurity> quoteBatch(Set<Security> s);
}
