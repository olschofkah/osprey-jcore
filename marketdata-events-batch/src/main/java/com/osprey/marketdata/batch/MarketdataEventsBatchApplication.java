package com.osprey.marketdata.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.osprey")
public class MarketdataEventsBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketdataEventsBatchApplication.class, args);
	}

}
