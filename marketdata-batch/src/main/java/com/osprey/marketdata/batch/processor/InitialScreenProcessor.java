package com.osprey.marketdata.batch.processor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.BasicStockScreenExecutor;
import com.osprey.screen.StockScreenContainer;
import com.osprey.screen.StockScreenPlan;
import com.osprey.screen.StockScreenPlanFactory;
import com.osprey.screen.criteria.IStockScreenCriteria;
import com.osprey.screen.criteria.StockScreenCriteriaGenerator;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;
import com.osprey.securitymaster.Security;

public class InitialScreenProcessor implements ItemProcessor<Security, Security> {

	final static Logger logger = LogManager.getLogger(InitialScreenProcessor.class);

	@Value("${initial.screen.set.json}")
	private String initialScreenJsonFile;

	@Override
	public Security process(Security item) throws Exception {

		logger.info("Performing initial screen on {} ", () -> item);

		InputStream in = getClass().getClassLoader().getResourceAsStream(initialScreenJsonFile);
		StockScreenContainer initialScreenContainer = new ObjectMapper().readValue(in, StockScreenContainer.class);

		Map<FundamentalPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>(2);
		securities.put(new FundamentalPricedSecurity(item), Collections.emptyList());

		// Convert the criteria generators into criteria.
		List<IStockScreenCriteria> criteria = new ArrayList<>(initialScreenContainer.getScreenCriteria().size());
		for (StockScreenCriteriaGenerator generator : initialScreenContainer.getScreenCriteria()) {
			criteria.add(generator.generate());
		}

		StockScreenPlanFactory stockScreenPlanFactory = new StockScreenPlanFactory(securities);
		List<StockScreenPlan> screenPlan = stockScreenPlanFactory.build(criteria);

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(screenPlan);
		executor.execute();

		if (executor.getResultSet().contains(item.getTicker())) {
			return item;
		} else {
			return null;
		}
	}

}
