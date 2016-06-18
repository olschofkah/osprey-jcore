package com.osprey.marketdata;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.osprey.marketdata.feed.mock.MockSecurityMasterService;
import com.osprey.marketdata.feed.nasdaq.NasdaqSecurityMasterFtpService;
import com.osprey.marketdata.feed.yahoo.YahooQuoteClient;
import com.osprey.marketdata.feed.yahoo.YahooQuoteUrlBuilder;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;
import com.osprey.securitymaster.repository.mock.MockSecurityMasterRepository;

@Configuration
@Profile("dev")
@ComponentScan("com.osprey")
@PropertySource("classpath:nasdaq.properties")
@PropertySource("classpath:yahoo.properties")
public class MarketDataDevConfiguration {

	@Bean
	public ISecurityMasterRepository securityMasterRepository() {
		return new MockSecurityMasterRepository();
	}

	@Bean
	public NasdaqSecurityMasterFtpService nasdaqSecurityMasterFtpService() {
		return new NasdaqSecurityMasterFtpService();
	}

	@Bean
	public MockSecurityMasterService mockSecurityMasterService() {
		return new MockSecurityMasterService();
	}

	@Bean
	public YahooQuoteClient yahooQuoteClient() {
		return new YahooQuoteClient();
	}
	
	@Bean
	@Scope("prototype")
	public YahooQuoteUrlBuilder yahooQuoteUrlBuilder(String symbol){
		return new YahooQuoteUrlBuilder(symbol);
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
