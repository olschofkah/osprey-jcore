package com.osprey.marketdata.batch.processor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.HotListItem;
import com.osprey.screen.ScreenCriteriaGenerator;
import com.osprey.screen.ScreenPlanFactory;
import com.osprey.screen.ScreenStrategyEntry;
import com.osprey.screen.SimpleScreenExecutor;
import com.osprey.screen.criteria.IScreenCriteria;
import com.osprey.screen.repository.IHotShitRepository;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class HistoricalModelProcessor implements ItemProcessor<SecurityQuoteContainer, SecurityQuoteContainer> {

	final static Logger logger = LogManager.getLogger(HistoricalModelProcessor.class);

	@Autowired
	private HotShitScreenProvidor screenProvidor;

	@Autowired
	private IHotShitRepository repo;

	private static int NUMBER_OF_HIST_PERIODS = 252; // TODO extract to config

	@Override
	public SecurityQuoteContainer process(SecurityQuoteContainer item) throws Exception {

		logger.info("Performing Historical Model runs on {} ", () -> item.getKey().getSymbol());

		if (item.getHistoricalQuotes().isEmpty()) {
			// If the history is empty ... just skip forward.
			return item;
		}

		Set<SecurityQuoteContainer> securities = new HashSet<>(2);
		securities.add(item);

		// Copy constructor to use for re-setting the hist back to where it was.
		List<HistoricalQuote> origHistQuoteList = new ArrayList<>(item.getHistoricalQuotes());

		ScreenPlanFactory screenPlanFactory = new ScreenPlanFactory(securities);
		SimpleScreenExecutor executor = new SimpleScreenExecutor();

		HotListItem result = null;
		List<HotListItem> hotListItems = new ArrayList<>();
		HistoricalQuote histQuote;

		LocalDate firstDate = null;
		LocalDate lastDate = null;

		Iterator<HistoricalQuote> iterator = item.getHistoricalQuotes().iterator();
		iterator.next();

		for (int i = 0; i < NUMBER_OF_HIST_PERIODS && item.getHistoricalQuotes().size() > 1; ++i) {

			// remove one day of OHLC data to calculate models back a day as if
			// it's the current day.
			iterator.remove();
			histQuote = iterator.next();
			lastDate = histQuote.getHistoricalDate();
			if (firstDate == null) {
				firstDate = lastDate;
			}

			for (ScreenStrategyEntry entry : screenProvidor.getScreens()) {

				try {

					// Convert the criteria generators into criteria.
					List<IScreenCriteria> criteria = new ArrayList<>(entry.getScreenCriteria().size());
					for (ScreenCriteriaGenerator generator : entry.getScreenCriteria()) {
						criteria.add(generator.generate());
					}

					executor.setPlans(screenPlanFactory.build(criteria));
					executor.execute();

					if (executor.getResultSet().contains(item.getKey())) {

						if (result == null) {
							result = new HotListItem(item.getKey());
						}
						result.addScreen(entry.getScreenName());
						result.addAllStrategies(entry.getStrategies());
						result.setReportDate(Date.valueOf(lastDate));
					}

				} catch (InsufficientHistoryException e) {
					logger.debug("Insufficient history to perform {} on {}",
							new Object[] { entry.getScreenName(), item.getKey().getSymbol() });
				}
			}

		}

		if (lastDate != null) {
			repo.deleteAndPersist(item.getKey().getSymbol(), lastDate, firstDate, hotListItems);
		}

		item.setHistoricalQuotes(origHistQuoteList);
		return item;
	}

}
