package com.osprey.screen.repository.jdbctemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.postgresql.util.PGobject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.osprey.screen.repository.IOspreyJSONObjectRepository;

// @Repository
public class OspreyJSONObjectJdbcRepository implements IOspreyJSONObjectRepository {

	private JdbcTemplate jdbc;

	public OspreyJSONObjectJdbcRepository(DataSource ds) {
		jdbc = new JdbcTemplate(ds);
	}

	@Override
	public String find(String key) {
		try {
			return jdbc.queryForObject("select obj_value from oc_map where obj_key = ?", String.class, key);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void persist(final String key, final String value) {
		final Timestamp now = Timestamp.valueOf(LocalDateTime.now());
		
		jdbc.update("insert into oc_map values (?,?,?)", new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, key);
				ps.setTimestamp(2, now);

				PGobject json = new PGobject();
				json.setType("json");
				json.setValue(value);

				ps.setObject(3, json);
			}
		});

	}

}
