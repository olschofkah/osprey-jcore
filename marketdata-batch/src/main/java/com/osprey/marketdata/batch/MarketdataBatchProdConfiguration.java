package com.osprey.marketdata.batch;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.integration.slack.SlackClient;

@Configuration
public class MarketdataBatchProdConfiguration {
	

	@Bean
	public DataSource postgresDataSource() {
		return DataSourceBuilder.create()
				.url("jdbc:postgresql://ospreydb.cl1fkmenjbzm.us-east-1.rds.amazonaws.com:5432/osprey01")
				.username("ospreyjavausr")
				.password("F4&^mfWXqazY")
				.type(BasicDataSource.class)
				.build();

		// TODO EXTRACT TO CONFIG !!!
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
		connFactory.setReadTimeout(30000); // TODO make config
		connFactory.setConnectTimeout(30000); // TODO make config

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(connFactory);
		return restTemplate;
	}
}
