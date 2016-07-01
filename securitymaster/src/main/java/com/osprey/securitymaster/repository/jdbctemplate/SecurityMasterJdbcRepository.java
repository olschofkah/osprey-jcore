package com.osprey.securitymaster.repository.jdbctemplate;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.osprey.securitymaster.EnhancedSecurity;
import com.osprey.securitymaster.FundamentalQuote;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityEvent;
import com.osprey.securitymaster.SecurityQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.SecurityUpcomingEvents;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;

// @Repository
public class SecurityMasterJdbcRepository implements ISecurityMasterRepository {

	private JdbcTemplate jdbc;

	public SecurityMasterJdbcRepository(DataSource ds) {
		jdbc = new JdbcTemplate(ds);
	}

	public double fetchClosingPrice(String symbol, LocalDate date) {
		// TODO Auto-generated method stub
		return 0;
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

	public void persist(EnhancedSecurity fq) {
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
}
