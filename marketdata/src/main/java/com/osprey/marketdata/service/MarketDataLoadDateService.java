package com.osprey.marketdata.service;

import java.io.IOException;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.repository.IOspreyJSONObjectRepository;
import com.osprey.securitymaster.utils.OspreyUtils;

public class MarketDataLoadDateService {

	@Autowired
	private IOspreyJSONObjectRepository objectRepository;
	@Autowired
	@Qualifier("om1")
	private ObjectMapper om;

	private static final String SM_LOAD_DATE_KEY = "sm-date";

	public void recordLoadDate(final ZonedDateTime today) {

		try {
			objectRepository.persist(SM_LOAD_DATE_KEY, om.writeValueAsString(new SecurityMasterLoadDateTime(today)));
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
			return OspreyUtils.getZonedDateTimeFromEpoch(loadDateTime.getMillsSinceEpoch());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
