package com.osprey.marketdata.feed;

import java.util.Map;
import java.util.Set;

import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuote;

public interface ILiveSecurityQuoteService {

	public SecurityQuote quote(SecurityKey s);

	public Map<Security, SecurityQuote> quoteBatch(Set<SecurityKey> s);
}
