package com.osprey.marketdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.osprey")
@SpringBootApplication
public class MarketdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketdataApplication.class, args);
	}
}
