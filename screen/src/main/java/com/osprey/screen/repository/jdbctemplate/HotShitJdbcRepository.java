package com.osprey.screen.repository.jdbctemplate;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.HotListItem;
import com.osprey.screen.repository.IHotShitRepository;

// @Repository
public class HotShitJdbcRepository implements IHotShitRepository {

	private JdbcTemplate jdbc;

	@Autowired
	@Qualifier("om1")
	private ObjectMapper om;

	public HotShitJdbcRepository(DataSource ds) {
		jdbc = new JdbcTemplate(ds);
	}

	@Override
	public List<HotListItem> findForDate(LocalDate dt) {
		final Date today = Date.valueOf(dt);

		List<String> result = jdbc.queryForList("select payload from tha_hot_shit where date = ?", String.class, today);

		List<HotListItem> hotItems = new ArrayList<>(result.size());
		for (String o : result) {
			HotListItem hotItem = null;
			try {
				hotItem = om.readValue(o, HotListItem.class);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			hotItems.add(hotItem);
		}

		return hotItems;
	}

	@Override
	public void deleteHotShitForDate(LocalDate dt) {
		jdbc.update("delete from tha_hot_shit where date = ?", Date.valueOf(dt));
	}

	@Override
	public void persistThaHotShit(List<? extends HotListItem> items) {

		final Timestamp now = Timestamp.valueOf(LocalDateTime.now());
		final Date today = Date.valueOf(LocalDate.now());

		jdbc.batchUpdate("insert into tha_hot_shit values (?,?,?,?)", new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				HotListItem sec = items.get(i);

				ps.setString(1, sec.getKey().getSymbol());
				ps.setDate(2, today);
				ps.setTimestamp(3, now);

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
