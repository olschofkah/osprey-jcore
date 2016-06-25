package com.osprey.marketdata.batch.processor;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.BasicScreenExecutor;
import com.osprey.screen.ScreenPlan;
import com.osprey.screen.ScreenPlanFactory;
import com.osprey.screen.ScreenStrategyEntry;
import com.osprey.screen.criteria.IStockScreenCriteria;
import com.osprey.screen.criteria.StockScreenCriteriaGenerator;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;

public class InitialScreenProcessor implements ItemProcessor<Security, Security> {

	final static Logger logger = LogManager.getLogger(InitialScreenProcessor.class);

	@Value("${initial.screen.set.json}")
	private String screenJsonFile;

	@Autowired
	private ISecurityMasterRepository securityMasterRepository;

	@Override
	public Security process(Security item) throws Exception {

		logger.info("Performing initial screen on {} ", () -> item.getSymbol());

		InputStream in = getClass().getClassLoader().getResourceAsStream(screenJsonFile);
		ScreenStrategyEntry entry = new ObjectMapper().readValue(in, ScreenStrategyEntry.class);

		// looking for a recent previous close for filtering
		double closingPrice = 0;
		for (int i = 1; i < 5 && closingPrice == 0; ++i) {
			closingPrice = securityMasterRepository.fetchClosingPrice(item.getSymbol(), LocalDate.now().minusDays(1));
		}

		FundamentalPricedSecurity emptyFundamental = new FundamentalPricedSecurity(item);
		emptyFundamental.setClose(closingPrice);

		Map<FundamentalPricedSecurity, List<HistoricalQuote>> securities = new HashMap<>(2);
		securities.put(emptyFundamental, Collections.emptyList());

		// Convert the criteria generators into criteria.
		List<IStockScreenCriteria> criteria = new ArrayList<>(entry.getScreenCriteria().size());
		for (StockScreenCriteriaGenerator generator : entry.getScreenCriteria()) {
			criteria.add(generator.generate());
		}

		ScreenPlanFactory screenPlanFactory = new ScreenPlanFactory(securities);
		List<ScreenPlan> screenPlan = screenPlanFactory.build(criteria);

		BasicScreenExecutor executor = new BasicScreenExecutor();
		executor.setPlans(screenPlan);
		executor.execute();

		if (executor.getResultSet().contains(item.getSymbol())) {
			return item;
		} else {
			return null;
		}
	}

}
