package com.osprey.securitymaster;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.osprey.securitymaster.repository.jdbctemplate.SecurityMasterJdbcRepository;

@Configuration
@Profile("test")
public class SecurityMasterTestConfiguration {
	
	@Autowired
	private DataSource ds;
	
	@Bean
	public DataSource postgresDataSource() throws ClassNotFoundException {
		
		Class.forName("org.postgresql.Driver");
		
		return DataSourceBuilder.create()
				.url("jdbc:postgresql://ospreydb.cl1fkmenjbzm.us-east-1.rds.amazonaws.com:5432/osprey01")
				.username("ospreyjavausr")
				.password("F4&^mfWXqazY")
				.type(BasicDataSource.class)
				.build();

		// TODO EXTRACT TO CONFIG !!!
	}

	@Bean
	public SecurityMasterJdbcRepository securityMasterRepository() {
		return new SecurityMasterJdbcRepository(ds);
	}

}
