package com.osprey.marketdata.batch.processor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.ScreenStrategyEntry;
import com.osprey.screen.repository.IOspreyJSONObjectRepository;

public class HotShitScreenProvidor {

	final static Logger logger = LogManager.getLogger(HotShitScreenProcessor.class);

	@Autowired
	private IOspreyJSONObjectRepository jsonRepository;

	@Autowired
	@Qualifier("om1")
	private ObjectMapper om;

	private List<ScreenStrategyEntry> screens;

	public List<ScreenStrategyEntry> getScreens() {
		return screens;
	}

	@PostConstruct
	public void init() {
		String hotShitScreens = jsonRepository.find("screens");

		logger.info("Using the following screens ... {}", () -> hotShitScreens);

		try {
			screens = Collections
					.unmodifiableList(om.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
							.readValue(hotShitScreens, new TypeReference<List<ScreenStrategyEntry>>() {
							}));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
