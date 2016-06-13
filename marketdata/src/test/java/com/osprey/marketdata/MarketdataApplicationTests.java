package com.osprey.marketdata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.osprey.marketdata.feed.mock.MockMarketDataFeedService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MarketDataDevConfiguration.class)
public class MarketdataApplicationTests {

	final static Logger logger = LogManager.getLogger(MockMarketDataFeedService.class);

	@Autowired
	private ConfigurableEnvironment env;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testAsyncLogger() {

		String property = env.getProperty("Log4jContextSelector");

		Assert.assertEquals("org.apache.logging.log4j.core.async.AsyncLoggerContextSelector", property);

		logger.info("Hello Logger World ... I should be async ... ");
		
	}

}
