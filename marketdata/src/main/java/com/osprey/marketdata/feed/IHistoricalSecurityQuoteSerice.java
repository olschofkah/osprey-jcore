package com.osprey.marketdata.feed;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.osprey.securitymaster.HistoricalSecurity;
import com.osprey.securitymaster.Security;

public interface IHistoricalSecurityQuoteSerice {

	public List<HistoricalSecurity> fetchHistorical(Security s, LocalDate start, LocalDate end);

	public Map<Security, List<HistoricalSecurity>> fetchHistoricalBatch(Set<Security> s, LocalDate start,
			LocalDate end);

}
