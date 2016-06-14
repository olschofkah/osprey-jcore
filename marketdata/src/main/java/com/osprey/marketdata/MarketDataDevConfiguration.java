package com.osprey.marketdata;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
@ComponentScan("com.osprey")
public class MarketDataDevConfiguration {
	
	

}
