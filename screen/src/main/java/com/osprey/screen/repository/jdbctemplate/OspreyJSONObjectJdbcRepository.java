package com.osprey.screen.repository.jdbctemplate;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.osprey.screen.repository.IOspreyJSONObjectRepository;

// @Repository
public class OspreyJSONObjectJdbcRepository implements IOspreyJSONObjectRepository {

	private JdbcTemplate jdbc;

	public OspreyJSONObjectJdbcRepository(DataSource ds) {
		jdbc = new JdbcTemplate(ds);
	}

	@Override
	public String find(String key) {
		return jdbc.queryForObject("select obj_value from oc_map where obj_key = ?", String.class, key);
	}

}
