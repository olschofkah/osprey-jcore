package com.osprey.marketdata;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.osprey.marketdata.feed.ISecurityMasterService;
import com.osprey.marketdata.feed.nasdaq.NasdaqSecurityMasterFtpService;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;
import com.osprey.securitymaster.repository.mock.MockSecurityMasterRepository;

@Configuration
@Profile("dev")
@ComponentScan("com.osprey")
@PropertySource("classpath:nasdaq.properties")
public class MarketDataDevConfiguration {

	@Bean
	public ISecurityMasterRepository securityMasterRepository() {
		return new MockSecurityMasterRepository();
	}

	@Bean
	public ISecurityMasterService securityMasterService() {
		return new NasdaqSecurityMasterFtpService();
	}

}
