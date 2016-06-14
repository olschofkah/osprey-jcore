package com.osprey.marketdata.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.osprey")
@SpringBootApplication
public class MarketdataRestApplication {


	public static void main(String[] args) {
		SpringApplication.run(MarketdataRestApplication.class, args);
	}

}
