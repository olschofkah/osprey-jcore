package com.osprey.screen.repository.jdbctemplate;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.math.OspreyJavaMath;
import com.osprey.screen.HotListItem;
import com.osprey.screen.ModelSymbolStatistic;
import com.osprey.screen.repository.IHotItemRepository;

// @Repository
public class HotItemJdbcRepository implements IHotItemRepository {

	final static Logger logger = LogManager.getLogger(HotItemJdbcRepository.class);

	private JdbcTemplate jdbc;
	private ObjectMapper om;

	public HotItemJdbcRepository(DataSource ds, ObjectMapper om) {
		jdbc = new JdbcTemplate(ds);
		this.om = om;
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
	public void deleteForDate(LocalDate dt) {
		jdbc.update("delete from tha_hot_shit where date = ? ", Date.valueOf(dt));
	}

	@Override
	public void deleteForDatesAndSymbol(String symbol, LocalDate earliestDate, LocalDate latestDate) {
		jdbc.update("delete from tha_hot_shit where symbol = ? and date >= ? and date <= ?", symbol,
				Date.valueOf(earliestDate), Date.valueOf(latestDate));
	}

	@Override
	public int findCountBySymbolAndDays(String symbol, int days, LocalDate startDate) {
		Date sqlDate = Date.valueOf(startDate.minusDays(days));

		int count = jdbc.queryForObject("select count(1) from tha_hot_shit where symbol = ? and date >= ? and date < ?",
				new Object[] { symbol, sqlDate, Date.valueOf(startDate) }, Integer.class);

		return count;
	}

	@Override
	public Map<String, Integer> findCountForModelBySymbolAndDays(String symbol, int days, LocalDate startDate) {
		Date sqlDate = Date.valueOf(startDate.minusDays(days));

		List<ModelSymbolStatistic> tmpResults = jdbc
				.query("select payload->'models'->0->'modelName' as model, count(1) "
						+ " from tha_hot_shit where symbol = ? and date >= ? and date < ?"
						+ " group by payload->'models'->0->'modelName'", new RowMapper<ModelSymbolStatistic>() {

							@Override
							public ModelSymbolStatistic mapRow(ResultSet rs, int rowNum) throws SQLException {
								String modelName = rs.getString("model");
								modelName = StringUtils.stripStart(modelName, "\"");
								modelName = StringUtils.stripEnd(modelName, "\"");
								
								return new ModelSymbolStatistic(rs.getInt("count"), modelName);
							}

						}, symbol, sqlDate, Date.valueOf(startDate));

		Map<String, Integer> results = new HashMap<>(OspreyJavaMath.calcMapInitialSize(tmpResults.size()));
		for (ModelSymbolStatistic stat : tmpResults) {
			results.put(stat.getModelName(), stat.getRecentOccurrence());
		}

		return results;
	}

	@Override
	public void persist(List<? extends HotListItem> items) {

		final Timestamp now = Timestamp.valueOf(LocalDateTime.now());

		jdbc.batchUpdate("insert into tha_hot_shit values (?,?,?,?)", new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				HotListItem sec = items.get(i);

				ps.setString(1, sec.getKey().getSymbol());
				ps.setDate(2, sec.getReportDate());
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

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void deleteAndPersist(String symbol, LocalDate earliestDate, LocalDate latestDate,
			List<? extends HotListItem> lst) {
		deleteForDatesAndSymbol(symbol, earliestDate, latestDate);
		if (!lst.isEmpty()) {
			persist(lst);
		}
	}

}
