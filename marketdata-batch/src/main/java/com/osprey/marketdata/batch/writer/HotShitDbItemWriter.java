package com.osprey.marketdata.batch.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.util.PGobject;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.ScreenSuccessSecurity;
import com.osprey.securitymaster.constants.OspreyConstants;

public class HotShitDbItemWriter implements ItemWriter<ScreenSuccessSecurity> {

	final static Logger logger = LogManager.getLogger(HotShitDbItemWriter.class);

	private JdbcTemplate jdbc;

	public HotShitDbItemWriter(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public void write(final List<? extends ScreenSuccessSecurity> items) throws Exception {

		final ObjectMapper om = new ObjectMapper();
		final java.sql.Date now = new java.sql.Date(ZonedDateTime.now().toInstant().toEpochMilli());
		final java.sql.Date today = new java.sql.Date(LocalDate.now()
				.atStartOfDay(ZoneId.of(OspreyConstants.ZONED_DATE_TIME_ZONE_ID_NY)).toInstant().toEpochMilli());

		logger.info("Persisting {} hotlist items ... ", () -> items.size());

		if (items.isEmpty()) {
			return;
		}

		jdbc.batchUpdate("insert into tha_hot_shit values (?,?,?,?)", new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ScreenSuccessSecurity sec = items.get(i);

				ps.setString(1, sec.getKey().getSymbol());
				ps.setDate(2, now);
				ps.setDate(3, today);

				String secJson = null;
				try {
					secJson = om.writeValueAsString(sec);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}

				PGobject json = new PGobject();
				json.setType("json");
				json.setValue(secJson);

				ps.setObject(4, json);

			}

			@Override
			public int getBatchSize() {
				return items.size();
			}
		});

	}

}
