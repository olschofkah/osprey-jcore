package com.osprey.marketdata.batch.processor;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.SimpleScreenExecutor;
import com.osprey.screen.ScreenCriteriaGenerator;
import com.osprey.screen.ScreenPlanFactory;
import com.osprey.screen.ScreenStrategyEntry;
import com.osprey.screen.ScreenSuccessSecurity;
import com.osprey.screen.criteria.IStockScreenCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class HotShitScreenProcessor implements ItemProcessor<SecurityQuoteContainer, ScreenSuccessSecurity> {

	final static Logger logger = LogManager.getLogger(HotShitScreenProcessor.class);

	@Value("${hot.shit.screen.set.json}")
	private String screenJsonFile;

	@Override
	public ScreenSuccessSecurity process(SecurityQuoteContainer item) throws Exception {

		logger.info("Performing tha hot shit on {} ", () -> item.getKey().getSymbol());

		InputStream in = getClass().getClassLoader().getResourceAsStream(screenJsonFile);
		List<ScreenStrategyEntry> entries = new ObjectMapper()
				.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
				.readValue(in, new TypeReference<List<ScreenStrategyEntry>>() {
				});

		Set<SecurityQuoteContainer> securities = new HashSet<>(2);
		securities.add(item);

		ScreenPlanFactory screenPlanFactory = new ScreenPlanFactory(securities);

		ScreenSuccessSecurity result = null;
		for (ScreenStrategyEntry entry : entries) {

			// Convert the criteria generators into criteria.
			List<IStockScreenCriteria> criteria = new ArrayList<>(entry.getScreenCriteria().size());
			for (ScreenCriteriaGenerator generator : entry.getScreenCriteria()) {
				criteria.add(generator.generate());
			}

			SimpleScreenExecutor executor = new SimpleScreenExecutor();
			executor.setPlans(screenPlanFactory.build(criteria));
			executor.execute();

			if (executor.getResultSet().contains(item.getKey())) {

				logger.info("Adding {} to the hot shit for {} ",
						new Object[] { item.getKey().getSymbol(), LocalDate.now() });

				if (result == null) {
					result = new ScreenSuccessSecurity(item.getKey());
				}
				result.addScreen(entry.getScreenName());
				result.addAllStrategies(entry.getStrategies());
			}
		}

		return result;
	}

}
