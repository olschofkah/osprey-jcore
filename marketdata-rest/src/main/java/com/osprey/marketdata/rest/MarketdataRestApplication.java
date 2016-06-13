package com.osprey.marketdata.rest;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.osprey.marketdata.rest.jsonserializer.ZonedDateTimeSerializer;

@ComponentScan("com.osprey")
@SpringBootApplication
public class MarketdataRestApplication {


	public static void main(String[] args) {
		SpringApplication.run(MarketdataRestApplication.class, args);
	}

}
