package com.osprey.marketdata.batch;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.integration.slack.SlackClient;

@Configuration
public class MarketdataBatchProdConfiguration {

	@Value("${spring.datasource.url}")
	private String dbUrl;
	@Value("${spring.datasource.username}")
	private String dbUser;
	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Value("${http.timeout}")
	private int httpTimeout;


	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public DataSource postgresDataSource() {
		return DataSourceBuilder.create()
				.url(dbUrl)
				.username(dbUser)
				.password(dbPassword)
				.type(BasicDataSource.class)
				.build();
	}
	

	@Bean
	public ObjectMapper om1() {
		return new ObjectMapper();
	}

	@Bean
	public SlackClient slackClient() {
		return new SlackClient(restTemplate(), om1());
	}

	@Bean
	public RestTemplate restTemplate() {

		// TODO Consider using OkHttpClientHttpRequestFactory
		SimpleClientHttpRequestFactory connFactory = new SimpleClientHttpRequestFactory();
		connFactory.setReadTimeout(httpTimeout); 
		connFactory.setConnectTimeout(httpTimeout); 

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(connFactory);
		return restTemplate;
	}
}
