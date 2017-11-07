package com.osprey.marketdata.batch;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

@Configuration
public class LogConfiguration {
	final static Logger logger = LogManager.getLogger(LogConfiguration.class);

	@Autowired
	private ConfigurableEnvironment env;

	@PostConstruct
	public void init() {
		logger.info("Setting env up for async logging ... ");

		MutablePropertySources propertySources = env.getPropertySources();
		Map<String, Object> systemPropertiesMap = new HashMap<>();

		// for async logging
		systemPropertiesMap.put("Log4jContextSelector",
				"org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");

		propertySources.addFirst(new MapPropertySource("CUSTOM_PROPERTIES", systemPropertiesMap));
	}

}
