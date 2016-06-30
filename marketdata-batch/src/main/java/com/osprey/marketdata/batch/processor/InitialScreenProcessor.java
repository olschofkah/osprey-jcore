package com.osprey.marketdata.batch.processor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.SimpleScreenExecutor;
import com.osprey.screen.ScreenPlan;
import com.osprey.screen.ScreenPlanFactory;
import com.osprey.screen.ScreenStrategyEntry;
import com.osprey.screen.criteria.IStockScreenCriteria;
import com.osprey.screen.criteria.StockScreenCriteriaGenerator;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class InitialScreenProcessor implements ItemProcessor<Security, SecurityQuoteContainer> {

	final static Logger logger = LogManager.getLogger(InitialScreenProcessor.class);

	@Value("${initial.screen.set.json}")
	private String screenJsonFile;

	@Override
	public SecurityQuoteContainer process(Security security) throws Exception {

		logger.info("Performing initial screen on {} ", () -> security.getKey());

		InputStream in = getClass().getClassLoader().getResourceAsStream(screenJsonFile);
		ScreenStrategyEntry entry = new ObjectMapper().readValue(in, ScreenStrategyEntry.class);

		SecurityQuoteContainer sqc = new SecurityQuoteContainer(security.getKey());
		sqc.setSecurity(security);

		Set<SecurityQuoteContainer> securities = new HashSet<>(2);
		securities.add(sqc);

		// Convert the criteria generators into criteria.
		List<IStockScreenCriteria> criteria = new ArrayList<>(entry.getScreenCriteria().size());
		for (StockScreenCriteriaGenerator generator : entry.getScreenCriteria()) {
			criteria.add(generator.generate());
		}

		ScreenPlanFactory screenPlanFactory = new ScreenPlanFactory(securities);
		List<ScreenPlan> screenPlan = screenPlanFactory.build(criteria);

		SimpleScreenExecutor executor = new SimpleScreenExecutor();
		executor.setPlans(screenPlan);
		executor.execute();

		if (executor.getResultSet().contains(security.getKey())) {
			return sqc;
		} else {
			return null;
		}
	}

}
