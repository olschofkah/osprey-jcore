package com.osprey.marketdata;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.osprey.securitymaster.repository.ISecurityMasterRepository;
import com.osprey.securitymaster.repository.mock.MockSecurityMasterRepository;

@Configuration
@Profile("dev")
@ComponentScan("com.osprey")
public class MarketDataDevConfiguration {

	@Bean
	public ISecurityMasterRepository securityMasterRepository() {
		return new MockSecurityMasterRepository();
	}

}
