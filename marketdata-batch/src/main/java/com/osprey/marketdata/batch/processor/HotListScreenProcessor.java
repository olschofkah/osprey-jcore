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

import com.osprey.screen.HotListItem;
import com.osprey.screen.ScreenCriteriaGenerator;
import com.osprey.screen.ScreenPlanFactory;
import com.osprey.screen.ScreenStrategyEntry;
import com.osprey.screen.SimpleScreenExecutor;
import com.osprey.screen.criteria.IScreenCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class HotListScreenProcessor implements ItemProcessor<SecurityQuoteContainer, HotListItem> {

	private final static Logger logger = LogManager.getLogger(HotListScreenProcessor.class);

	private HotListScreenProvidor screenProvidor;
	private InitialScreenService initialScreenService;

	public HotListScreenProcessor(HotListScreenProvidor screenProvidor, InitialScreenService initialScreenService) {
		this.screenProvidor = screenProvidor;
		this.initialScreenService = initialScreenService;
	}

	@Override
	public HotListItem process(SecurityQuoteContainer item) throws Exception {

		logger.info("Performing tha hot list on {} ", () -> item.getKey().getSymbol());

		Set<SecurityQuoteContainer> securities = new HashSet<>(2);
		securities.add(item);

		ScreenPlanFactory screenPlanFactory = new ScreenPlanFactory(securities);

		Date today = Date.valueOf(LocalDate.now());
		HotListItem result = null;
		if (item.getFundamentalQuote() != null) {
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

					logger.info("Adding {} to the hot list for {} ",
							new Object[] { item.getKey().getSymbol(), LocalDate.now() });

					if (result == null) {
						result = new HotListItem(item.getKey());
					}
					result.addModel(entry.getScreenName());
					result.addAllStrategies(entry.getStrategies());
					result.setReportDate(today);
				}
			}
		} else {
			logger.warn("Skipping security {} due to missing fundamental data", () -> item.getKey().getSymbol());
		}

		return result;
	}

}
