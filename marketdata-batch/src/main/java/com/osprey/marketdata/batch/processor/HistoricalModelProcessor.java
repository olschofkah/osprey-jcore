package com.osprey.marketdata.batch.processor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.HotListItem;
import com.osprey.screen.ScreenCriteriaGenerator;
import com.osprey.screen.ScreenPlanFactory;
import com.osprey.screen.ScreenStrategyEntry;
import com.osprey.screen.SimpleScreenExecutor;
import com.osprey.screen.criteria.IScreenCriteria;
import com.osprey.screen.repository.IHotItemRepository;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityEvent;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.SecurityUpcomingEvents;
import com.osprey.securitymaster.constants.SecurityEventType;

public class HistoricalModelProcessor implements ItemProcessor<SecurityQuoteContainer, SecurityQuoteContainer> {

	private final static Logger logger = LogManager.getLogger(HistoricalModelProcessor.class);

	private HotListScreenProvidor screenProvidor;
	private IHotItemRepository repo;

	@Value("${model.hist.periods}")
	private int NUMBER_OF_HIST_PERIODS;

	private static boolean HIST_ENABLED = true;

	public HistoricalModelProcessor(HotListScreenProvidor screenProvidor, IHotItemRepository repo) {
		this.screenProvidor = screenProvidor;
		this.repo = repo;
	}

	@Override
	public SecurityQuoteContainer process(SecurityQuoteContainer sqc) throws Exception {

		logger.info("Performing Historical Model runs on {} ", () -> sqc.getKey().getSymbol());

		if (!HIST_ENABLED || sqc.getHistoricalQuotes().isEmpty()) {
			return sqc;
		}

		Set<SecurityQuoteContainer> securities = new HashSet<>(2);
		securities.add(sqc);

		// Copy to use for re-setting the hist back to where it was.
		List<HistoricalQuote> origHistQuoteList = new ArrayList<>(sqc.getHistoricalQuotes());

		SecurityUpcomingEvents origUpcomingEvents = sqc.getUpcomingEvents();
		sqc.setUpcomingEvents(new SecurityUpcomingEvents(origUpcomingEvents));

		List<HotListItem> hotListItems = new ArrayList<>();
		HotListItem hotListItem;
		Map<SecurityKey, HotListItem> itemMap;
		HistoricalQuote histQuote;

		LocalDate firstDate = null;
		LocalDate lastDate = null;

		Iterator<HistoricalQuote> iterator = sqc.getHistoricalQuotes().iterator();
		iterator.next();

		ScreenPlanFactory screenPlanFactory = new ScreenPlanFactory(securities);
		SimpleScreenExecutor executor;

		sqc.sortEventsDescending();

		for (int i = 0; i < NUMBER_OF_HIST_PERIODS && sqc.getHistoricalQuotes().size() > 1; ++i) {

			// remove one day of OHLC data to calculate models back a day as if
			// it's the current day.
			iterator.remove();
			histQuote = iterator.next();

			lastDate = histQuote.getHistoricalDate();
			if (firstDate == null) {
				firstDate = lastDate;
			}

			populateHistoricalEvents(sqc, histQuote.getHistoricalDate());

			itemMap = new HashMap<>();

			for (ScreenStrategyEntry entry : screenProvidor.getScreens()) {
				executor = new SimpleScreenExecutor();

				try {

					// Convert the criteria generators into criteria.
					List<IScreenCriteria> criteria = new ArrayList<>(entry.getScreenCriteria().size());
					for (ScreenCriteriaGenerator generator : entry.getScreenCriteria()) {
						criteria.add(generator.generate());
					}

					executor.setPlans(screenPlanFactory.build(criteria));
					executor.execute();

					if (executor.getResultSet().contains(sqc.getKey())) {

						if (!itemMap.containsKey(sqc.getKey())) {
							hotListItem = new HotListItem(sqc.getKey());
							hotListItems.add(hotListItem);
							hotListItem.setReportDate(Date.valueOf(lastDate));
							hotListItem.setRecentCount(repo.findCountBySymbolAndDays(sqc.getKey().getSymbol(), 10,
									histQuote.getHistoricalDate()));

							itemMap.put(sqc.getKey(), hotListItem);
						}
						hotListItem = itemMap.get(sqc.getKey());
						hotListItem.addModel(entry.getScreenName());
						hotListItem.addAllStrategies(entry.getStrategies());
					}

				} catch (InsufficientHistoryException e) {
					logger.debug("Insufficient history to perform {} on {}",
							new Object[] { entry.getScreenName(), sqc.getKey().getSymbol() });
				}
			}

			Map<String, Integer> modelStatMap;
			for (HotListItem item : itemMap.values()) {
				modelStatMap = repo.findCountForModelBySymbolAndDays(sqc.getKey().getSymbol(), 10,
						histQuote.getHistoricalDate());
				item.addModelCounts(modelStatMap);
			}

		}

		if (lastDate != null) {
			repo.deleteAndPersist(sqc.getKey().getSymbol(), lastDate, firstDate, hotListItems);
		}

		sqc.setHistoricalQuotes(origHistQuoteList);
		sqc.setUpcomingEvents(origUpcomingEvents);

		return sqc;
	}

	private void populateHistoricalEvents(SecurityQuoteContainer item, LocalDate historicalDate) {
		SecurityUpcomingEvents upcomingEvents = item.getUpcomingEvents();

		boolean earningsSet = false;
		boolean revenueSet = false;
		boolean divSet = false;
		boolean exDivSet = false;

		// this is pre-sorted
		for (SecurityEvent event : item.getEvents()) {
			if (!earningsSet && event.getEvent() == SecurityEventType.EARNINGS_ACT
					&& !historicalDate.isAfter(event.getDate())) {
				upcomingEvents.setNextEarningsDateEstLow(event.getDate());
				upcomingEvents.setNextEarningsDateEstHigh(event.getDate());
				earningsSet = true;
			}

			if (!revenueSet && event.getEvent() == SecurityEventType.REVENUE
					&& !historicalDate.isAfter(event.getDate())) {
				upcomingEvents.setNextRevenue(event.getDate());
				earningsSet = true;
			}

			if (!exDivSet && event.getEvent() == SecurityEventType.EX_DIV && !historicalDate.isAfter(event.getDate())) {
				upcomingEvents.setNextExDivDate(event.getDate());
				exDivSet = true;
			}

			if (!divSet && event.getEvent() == SecurityEventType.DIV && !historicalDate.isAfter(event.getDate())) {
				upcomingEvents.setNextDivDate(event.getDate());
				divSet = true;
			}
		}
	}

}
