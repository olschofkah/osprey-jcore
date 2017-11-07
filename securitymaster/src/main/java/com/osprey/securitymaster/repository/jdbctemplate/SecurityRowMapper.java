package com.osprey.securitymaster.repository.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.constants.Exchange;
import com.osprey.securitymaster.constants.InstrumentType;
import com.osprey.securitymaster.utils.OspreyUtils;

public class SecurityRowMapper implements RowMapper<Security> {

	public Security mapRow(ResultSet rs, int rowNum) throws SQLException {
		Security security = new Security(new SecurityKey(rs.getString("symbol"), rs.getString("cusip")));
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

}
