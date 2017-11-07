package com.osprey.marketdata.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.osprey")
public class MarketdataBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketdataBatchApplication.class, args);
	}

}
