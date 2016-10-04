package com.osprey.marketdata.batch.processor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.ScreenStrategyEntry;
import com.osprey.screen.repository.IOspreyJSONObjectRepository;

public class HotListScreenProvidor {

	private final static Logger logger = LogManager.getLogger(HotListScreenProcessor.class);

	private IOspreyJSONObjectRepository jsonRepository;
	private ObjectMapper om;

	private List<ScreenStrategyEntry> screens;

	public HotListScreenProvidor(IOspreyJSONObjectRepository jsonRepository, ObjectMapper om) {
		this.jsonRepository = jsonRepository;
		this.om = om;
	}

	public List<ScreenStrategyEntry> getScreens() {
		return screens;
	}

	@PostConstruct
	public void init() {
		String hotListScreens = jsonRepository.find("screens");

		logger.info("Using the following screens ... {}", () -> hotListScreens);

		try {
			screens = Collections
					.unmodifiableList(om.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
							.readValue(hotListScreens, new TypeReference<List<ScreenStrategyEntry>>() {
							}));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
