package com.osprey.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.integration.slack.SlackClient;

@Configuration
@Profile("integration-test")
public class IntegrationTestConfiguration {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
	    return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public SlackClient slack(){
		return new SlackClient(restTemplate(), om1());
	}

	@Bean
	public ObjectMapper om1() {
		return new ObjectMapper();
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
