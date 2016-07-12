package com.osprey.securitymaster.repository.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.osprey.securitymaster.EnhancedSecurity;
import com.osprey.securitymaster.FundamentalQuote;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityEvent;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.SecurityUpcomingEvents;
import com.osprey.securitymaster.constants.Exchange;
import com.osprey.securitymaster.constants.InstrumentType;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;
import com.osprey.securitymaster.utils.OspreyUtils;

// @Repository
public class SecurityMasterJdbcRepository implements ISecurityMasterRepository {

	private JdbcTemplate jdbc;

	public SecurityMasterJdbcRepository(DataSource ds) {
		jdbc = new JdbcTemplate(ds);
	}

	private static final String SELECT_OC_SECURITY = " select symbol, cusip, instrument_cd, lot_size, company_name, country, state, employee_cnt, industry, sector, exchange_cd, currency, last_update_ts, description, previous_close "
			+ " from oc_security where symbol = ?";
	private static final String UPDATE_OC_SECURITY = "update oc_security "
			+ " set description = ?, company_name = ?, country = ?, currency = ?, employee_cnt = ?, exchange_cd = ?, industry = ?, instrument_cd = ?, lot_size = ?, sector = ?, state = ?, last_update_ts = clock_timestamp(), previous_close = ?"
			+ " where symbol = ? ";
	private static final String INSERT_OC_SECURITY = " insert into oc_security "
			+ " (symbol, cusip, instrument_cd, lot_size, company_name, country, state, employee_cnt, industry, sector, exchange_cd, currency, last_update_ts, create_ts, description, previous_close) "
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, clock_timestamp(), clock_timestamp(), ?, ?) ";
	private static final String EXISTS_OC_SECURITY = "select exists (select 1 from oc_security where symbol = ? )";

	public Security findSecurity(final SecurityKey key) {
		return jdbc.queryForObject(SELECT_OC_SECURITY, new RowMapper<Security>() {

			public Security mapRow(ResultSet rs, int rowNum) throws SQLException {
				Security security = new Security(key);
				security.setCompanyDescription(rs.getString("description"));
				security.setCompanyName(rs.getString("company_name"));
				security.setCountry(rs.getString("country"));
				security.setCurrency(rs.getString("currency"));
				security.setEmployeeCount(rs.getInt("employee_cnt"));
				security.setExchange(Exchange.fromCode(rs.getString("exchange_cd")));
				security.setIndustry(rs.getString("industry"));
				security.setInstrumentType(InstrumentType.fromId(rs.getInt("instrument_cd")));
				security.setLotSize(rs.getInt("lot_size"));
				security.setSector(rs.getString("sector"));
				security.setState(rs.getString("state"));
				security.setPreviousClose(rs.getDouble("previous_close"));

				Timestamp timestamp = rs.getTimestamp("last_update_ts");
				security.setTimestamp(OspreyUtils.getZonedDateTimeFromEpoch(timestamp.getTime()));

				return security;
			}
		}, key.getSymbol());

	}

	public double fetchClosingPrice(String symbol, LocalDate date) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(SecurityQuoteContainer sqc) {
		// TODO Auto-generated method stub

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(SecurityQuote sq) {
		// TODO Auto-generated method stub

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(Security s) {

		boolean exists = jdbc.queryForObject(EXISTS_OC_SECURITY, new Object[] { s.getKey().getSymbol() },
				Boolean.class);

		if (exists) {
			// update
			jdbc.update(UPDATE_OC_SECURITY, s.getCompanyDescription(), s.getCompanyName(), s.getCountry(),
					s.getCurrency(), s.getEmployeeCount(), s.getExchange().getCode(), s.getIndustry(),
					s.getInstrumentType().getId(), s.getLotSize(), s.getSector(), s.getState(), s.getKey().getSymbol(),
					s.getPreviousClose());

		} else {
			// insert
			jdbc.update(INSERT_OC_SECURITY, s.getKey().getSymbol(), s.getKey().getCusip(),
					s.getInstrumentType().getId(), s.getLotSize(), s.getCompanyName(), s.getCountry(), s.getState(),
					s.getEmployeeCount(), s.getIndustry(), s.getSector(), s.getExchange().getCode(), s.getCurrency(),
					s.getCompanyDescription(), s.getPreviousClose());
		}

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(FundamentalQuote fq) {
		// TODO Auto-generated method stub

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(EnhancedSecurity fq) {
		// TODO Auto-generated method stub

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persist(SecurityUpcomingEvents fq) {
		// TODO Auto-generated method stub

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persistEvents(List<SecurityEvent> events) {
		// TODO Auto-generated method stub

	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void persistHistoricals(List<HistoricalQuote> hist) {
		// TODO Auto-generated method stub

	}
}
