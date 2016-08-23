package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.EarningsVolatilityAverageCriteria;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityEvent;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;
import com.osprey.securitymaster.constants.SecurityEventType;

public class EarningsVolatilityAverageScreen implements IStockScreen {

	private final EarningsVolatilityAverageCriteria criteria;

	private boolean passed;

	public EarningsVolatilityAverageScreen(EarningsVolatilityAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		if (sqc.getEvents() == null) {
			// no earnings found
			passed = false; // should already be false.
			return this;
		}

		int period = criteria.getDaysAfter() + criteria.getDaysBefore() + 1;

		Queue<SecurityEvent> eventQueue = new LinkedList<>(sqc.getEvents());
		SecurityEvent currentEarningsEvent = null;
		SecurityEvent currentEvent;

		while (currentEarningsEvent == null && !eventQueue.isEmpty()) {
			currentEvent = eventQueue.poll();
			if (currentEvent.getEvent() == SecurityEventType.EARNINGS_ACT) {
				currentEarningsEvent = currentEvent;
				break;
			}
		}

		if (currentEarningsEvent == null) {
			// no earnings found
			passed = false; // should already be false.
			return this;
		}

		List<HistoricalQuote> currentQuotePeriodList = new ArrayList<>(period);

		int daysAfterPlusOne = criteria.getDaysAfter() + 1;
		int daysBeforePlusOne = criteria.getDaysBefore() + 1;

		double volatilityAverage = 0.0;
		double volatilityCount = 0;

		boolean trigger;
		for (HistoricalQuote hq : sqc.getHistoricalQuotes()) {
			if (hq.getHistoricalDate().isBefore(currentEarningsEvent.getDate().plusDays(daysAfterPlusOne))) {
				// check that the date is in the upper bound

				if (hq.getHistoricalDate().isAfter(currentEarningsEvent.getDate().minusDays(daysBeforePlusOne))) {
					// check that the date is in the lower bound

					currentQuotePeriodList.add(hq);
					trigger = false;
				} else {
					// just existed an earnings range ... trigger a calc and
					// reset.
					trigger = true;
				}
			} else {
				// keep looking for the next date in the range
				trigger = false;
			}

			if (trigger) {
				// done collecting data for this event, go to the next and calc
				// this vol

				// calculate only if there were any prices found to use ...
				// needs at least 3 periods to calculate vol.
				if (currentQuotePeriodList.size() > 2) {
					volatilityAverage += OspreyQuantMath.volatility(currentQuotePeriodList.size(),
							currentQuotePeriodList);
					++volatilityCount;
				}

				// clear the list for the next calc
				currentQuotePeriodList.clear();

				// find the next earnings event
				currentEarningsEvent = null;
				while (currentEarningsEvent == null && !eventQueue.isEmpty()) {
					currentEvent = eventQueue.poll();
					if (currentEvent.getEvent() == SecurityEventType.EARNINGS_ACT) {
						currentEarningsEvent = currentEvent;
						break;
					}
				}

				// stop if we're out of earnings to average.
				if (currentEarningsEvent == null) {
					break;
				}
			}
		}

		// average the results
		if (volatilityCount != 0) {
			volatilityAverage /= volatilityCount;
		}

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(volatilityAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getVolatilityComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(volatilityAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getVolatilityComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = volatilityAverage > criteria.getVolatilityComparison();
			break;
		case _LE:
			passed = new BigDecimal(volatilityAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getVolatilityComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = volatilityAverage < criteria.getVolatilityComparison();
			break;
		default:
			passed = false;
			break;

		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
