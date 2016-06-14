package com.osprey.marketdata.rest;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.osprey.marketdata.rest.jsonserializer.ZonedDateTimeSerializer;

@Configuration
@Profile("test")
public class MarketDataRestTestConfiguration {

	@Autowired
	private ZonedDateTimeSerializer zonedDateTimeSerializer;

	@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
		Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
		b.serializerByType(ZonedDateTime.class, zonedDateTimeSerializer);
		return b;
	}

}
