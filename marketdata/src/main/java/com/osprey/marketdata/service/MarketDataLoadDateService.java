package com.osprey.marketdata.service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.repository.IOspreyJSONObjectRepository;

public class MarketDataLoadDateService {

	private IOspreyJSONObjectRepository objectRepository;
	private ObjectMapper om;

	private static final String SM_LOAD_DATE_KEY = "sm-date";

	public MarketDataLoadDateService(IOspreyJSONObjectRepository objectRepository, ObjectMapper om) {
		this.objectRepository = objectRepository;
		this.om = om;
	}

	public void recordLoadDate(final ZonedDateTime today) {

		try {
			objectRepository.persist(SM_LOAD_DATE_KEY, om
					.writeValueAsString(new SecurityMasterLoadDateTime(today.format(DateTimeFormatter.ISO_DATE_TIME))));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public ZonedDateTime findLastLoadDate() {
		String obj = objectRepository.find(SM_LOAD_DATE_KEY);

		if (obj == null) {
			return ZonedDateTime.now().minusYears(32);
		}

		try {
			SecurityMasterLoadDateTime loadDateTime = om.readValue(obj, SecurityMasterLoadDateTime.class);
			return ZonedDateTime.parse(loadDateTime.getDateString(), DateTimeFormatter.ISO_DATE_TIME);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
