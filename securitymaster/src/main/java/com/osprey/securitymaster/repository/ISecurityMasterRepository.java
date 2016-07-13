package com.osprey.securitymaster.repository;

import java.time.LocalDate;
import java.util.List;

import com.osprey.securitymaster.EnhancedSecurity;
import com.osprey.securitymaster.FundamentalQuote;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityEvent;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.SecurityUpcomingEvents;

public interface ISecurityMasterRepository {

	public double fetchClosingPrice(String ticker, LocalDate date);

	public void persist(SecurityQuoteContainer sqc);

	public void persist(SecurityQuote sq);

	public void persist(Security s);

	public void persist(FundamentalQuote fq);

	public void persist(EnhancedSecurity fq);

	public void persist(SecurityUpcomingEvents fq);

	public void persistEvents(List<SecurityEvent> events);

	public void persistHistoricals(List<HistoricalQuote> hist);

	void deleteHistoricals(SecurityKey key);

	List<HistoricalQuote> findHistoricals(SecurityKey key, LocalDate minDate, LocalDate maxDate);

}
