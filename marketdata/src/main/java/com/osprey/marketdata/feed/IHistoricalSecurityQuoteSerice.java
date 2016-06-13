package com.osprey.marketdata.feed;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.osprey.securitymaster.HistoricalSecurity;
import com.osprey.securitymaster.Security;

public interface IHistoricalSecurityQuoteSerice {

	public List<HistoricalSecurity> fetchHistorical(Security s, ZonedDateTime start, ZonedDateTime end);

	public Map<Security, List<HistoricalSecurity>> fetchHistoricalBatch(Set<Security> s, ZonedDateTime start,
			ZonedDateTime end);

}
