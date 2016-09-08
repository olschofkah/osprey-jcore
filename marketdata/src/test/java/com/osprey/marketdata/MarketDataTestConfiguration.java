package com.osprey.marketdata;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.marketdata.feed.constants.QuoteDataFrequency;
import com.osprey.marketdata.feed.nasdaq.NasdaqSecurityMasterFtpService;
import com.osprey.marketdata.feed.yahoo.YahooHistoricalQuoteClient;
import com.osprey.marketdata.feed.yahoo.YahooHistoricalUrlBuilder;
import com.osprey.marketdata.feed.yahoo.YahooQuoteClient;
import com.osprey.marketdata.feed.yahoo.YahooQuoteUrlBuilder;
import com.osprey.marketdata.feed.ychart.YChartHistoricalEventsClient;
import com.osprey.marketdata.service.MarketDataLoadDateService;
import com.osprey.screen.repository.IOspreyJSONObjectRepository;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;
import com.osprey.securitymaster.repository.mock.MockSecurityMasterRepository;

@Configuration
@Profile("integration-test")
public class MarketDataTestConfiguration {
	
	@Value("${http.timeout}")
	private int httpTimeout;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
	    return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public ISecurityMasterRepository securityMasterRepository() {
		return new MockSecurityMasterRepository();
	}

	@Bean
	public NasdaqSecurityMasterFtpService nasdaqSecurityMasterFtpService() {
		return new NasdaqSecurityMasterFtpService(marketDataLoadDateService());
	}

	@Bean
	public YahooHistoricalQuoteClient yahooHistoricalQuoteClient() {
		return new YahooHistoricalQuoteClient();
	}

	@Bean
	public YahooQuoteClient yahooQuoteClient() {
		return new YahooQuoteClient(restTemplate());
	}
	
	@Bean YChartHistoricalEventsClient ychartHistoricalEventsClient(){
		return new YChartHistoricalEventsClient(restTemplate());
	}

	@Bean
	@Scope("prototype")
	public YahooQuoteUrlBuilder yahooQuoteUrlBuilder(String symbol) {
		return new YahooQuoteUrlBuilder(symbol);
	}

	@Bean
	@Scope("prototype")
	public YahooHistoricalUrlBuilder yahooHistoricalUrlBuilder(String symbol, LocalDate start, LocalDate end,
			QuoteDataFrequency freq) {
		return new YahooHistoricalUrlBuilder(symbol, start, end, freq);
	}

	@Bean
	public MarketDataLoadDateService marketDataLoadDateService() {
		return new MarketDataLoadDateService(ospreyJSONObjectRepository(), om1());
	}

	@Bean
	public IOspreyJSONObjectRepository ospreyJSONObjectRepository() {
		return new IOspreyJSONObjectRepository() {

			@Override
			public String find(String key) {
				return null;
			}

			@Override
			public void persist(String key, String value) {
			}
		};
	}

	@Bean
	public ObjectMapper om1() {
		return new ObjectMapper();
	}

	@Bean
	public RestTemplate restTemplate() {

		SimpleClientHttpRequestFactory connFactory = new SimpleClientHttpRequestFactory();
		connFactory.setReadTimeout(httpTimeout);
		connFactory.setConnectTimeout(httpTimeout); 

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(connFactory);
		return restTemplate;
	}
}
