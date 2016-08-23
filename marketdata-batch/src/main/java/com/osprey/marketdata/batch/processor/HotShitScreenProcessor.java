package com.osprey.marketdata.batch.processor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.osprey.screen.HotListItem;
import com.osprey.screen.ScreenCriteriaGenerator;
import com.osprey.screen.ScreenPlanFactory;
import com.osprey.screen.ScreenStrategyEntry;
import com.osprey.screen.SimpleScreenExecutor;
import com.osprey.screen.criteria.IScreenCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class HotShitScreenProcessor implements ItemProcessor<SecurityQuoteContainer, HotListItem> {

	final static Logger logger = LogManager.getLogger(HotShitScreenProcessor.class);

	@Autowired
	private HotShitScreenProvidor screenProvidor;
	@Autowired
	private InitialScreenService initialScreenService;

	@Override
	public HotListItem process(SecurityQuoteContainer item) throws Exception {

		logger.info("Performing tha hot shit on {} ", () -> item.getKey().getSymbol());

		Set<SecurityQuoteContainer> securities = new HashSet<>(2);
		securities.add(item);

		ScreenPlanFactory screenPlanFactory = new ScreenPlanFactory(securities);

		Date today = Date.valueOf(LocalDate.now());
		HotListItem result = null;
		for (ScreenStrategyEntry entry : screenProvidor.getScreens()) {

			// Convert the criteria generators into criteria.
			List<IScreenCriteria> criteria = new ArrayList<>(entry.getScreenCriteria().size());
			for (ScreenCriteriaGenerator generator : entry.getScreenCriteria()) {
				criteria.add(generator.generate());
			}
			
			criteria.addAll(initialScreenService.getCriteria());

			SimpleScreenExecutor executor = new SimpleScreenExecutor();
			executor.setPlans(screenPlanFactory.build(criteria));
			executor.execute();

			if (executor.getResultSet().contains(item.getKey())) {

				logger.info("Adding {} to the hot shit for {} ",
						new Object[] { item.getKey().getSymbol(), LocalDate.now() });

				if (result == null) {
					result = new HotListItem(item.getKey());
				}
				result.addScreen(entry.getScreenName());
				result.addAllStrategies(entry.getStrategies());
				result.setReportDate(today);
			}
		}

		return result;
	}

}
