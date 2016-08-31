package com.osprey.securitymaster;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.osprey.securitymaster.repository.jdbctemplate.SecurityMasterJdbcRepository;

@Configuration
@Profile("integration-test")
public class SecurityMasterTestConfiguration {

	@Value("${spring.datasource.url}")
	private String dbUrl;
	@Value("${spring.datasource.username}")
	private String dbUser;
	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public DataSource postgresDataSource() throws ClassNotFoundException {

		Class.forName("org.postgresql.Driver");

		return DataSourceBuilder.create()
				.url(dbUrl)
				.username(dbUser)
				.password(dbPassword)
				.type(BasicDataSource.class)
				.build();

	}

	@Bean
	public SecurityMasterJdbcRepository securityMasterRepository() throws ClassNotFoundException {
		return new SecurityMasterJdbcRepository(postgresDataSource());
	}

}
