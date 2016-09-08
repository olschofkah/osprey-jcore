package com.osprey.securitymaster.repository.mock;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.osprey.securitymaster.FundamentalQuote;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityEvent;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.SecurityUpcomingEvents;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;

public class MockSecurityMasterRepository implements ISecurityMasterRepository {
	
	public MockSecurityMasterRepository(){
		
	}

	public double fetchClosingPrice(String ticker, LocalDate date) {
		return RandomUtils.nextDouble(6, 120);
	}

	public void persist(SecurityQuoteContainer sqc) {
		// TODO Auto-generated method stub
		
	}

	public void persist(SecurityQuote sq) {
		// TODO Auto-generated method stub
		
	}

	public void persist(Security s) {
		// TODO Auto-generated method stub
		
	}

	public void persist(FundamentalQuote fq) {
		// TODO Auto-generated method stub
		
	}

	public void persist(SecurityUpcomingEvents fq) {
		// TODO Auto-generated method stub
		
	}

	public void persistEvents(List<SecurityEvent> events) {
		// TODO Auto-generated method stub
		
	}

	public void persistHistoricals(List<HistoricalQuote> hist) {
		// TODO Auto-generated method stub
		
	}

	public void deleteThaHotShitForDay(LocalDate date) {
		// TODO Auto-generated method stub
		
	}

	public void deleteHistoricals(SecurityKey key) {
		// TODO Auto-generated method stub
		
	}

	public List<HistoricalQuote> findHistoricals(SecurityKey key, LocalDate minDate, LocalDate maxDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Security> findSecurities() {
		// TODO Auto-generated method stub
		return null;
	}

	public Security findSecurity(SecurityKey key) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SecurityEvent> findSecurityEvents(SecurityKey key) {
		// TODO Auto-generated method stub
		return null;
	}

	public SecurityQuoteContainer findSecurityQuoteContainer(SecurityKey key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecurityQuoteContainer findSecurityQuoteContainer(SecurityKey key, LocalDate minDate, LocalDate maxDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persistEventsAndFundamentals(SecurityQuoteContainer sqc) {
		// TODO Auto-generated method stub
		
	}

}
