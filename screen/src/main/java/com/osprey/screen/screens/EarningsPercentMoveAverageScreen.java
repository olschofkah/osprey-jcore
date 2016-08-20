package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Queue;

import com.osprey.screen.criteria.EarningsPercentMoveAverageCriteria;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityEvent;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;
import com.osprey.securitymaster.constants.SecurityEventType;

public class EarningsPercentMoveAverageScreen implements IStockScreen {

	private final EarningsPercentMoveAverageCriteria criteria;

	private boolean passed;

	public EarningsPercentMoveAverageScreen(EarningsPercentMoveAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

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

		int daysAfterPlusOne = criteria.getDaysAfter() + 1;
		int daysBeforePlusOne = criteria.getDaysBefore() + 1;

		double moveAverage = 0.0;
		double a = 0.0;
		double b = 0.0;
		double count = 0;

		boolean trigger;
		for (HistoricalQuote hq : sqc.getHistoricalQuotes()) {
			if (hq.getHistoricalDate().isBefore(currentEarningsEvent.getDate().plusDays(daysAfterPlusOne))) {
				// check that the date is in the upper bound

				if (hq.getHistoricalDate().isAfter(currentEarningsEvent.getDate().minusDays(daysBeforePlusOne))) {
					// check that the date is in the lower bound

					if (a == 0.0) {
						// store the most recent close in a
						a = hq.getAdjClose();
					} else {
						// store the oldest close in b
						b = hq.getAdjClose();
					}
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

				if (a != 0.0) {
					if (b == 0.0) {
						b = a;
					}
					moveAverage += Math.abs(1.0 - b / a);
					++count;
				}
				a = b = 0.0;

				currentEarningsEvent = null;
				while (currentEarningsEvent == null && !eventQueue.isEmpty()) {
					currentEvent = eventQueue.poll();
					if (currentEvent.getEvent() == SecurityEventType.EARNINGS_ACT) {
						currentEarningsEvent = currentEvent;
						break;
					}
				}

				if (currentEarningsEvent == null) {
					break;
				}
			}
		}

		if (count != 0) {
			moveAverage /= count;
		}

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(moveAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getPercentMoveComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(moveAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getPercentMoveComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = moveAverage > criteria.getPercentMoveComparison();
			break;
		case _LE:
			passed = new BigDecimal(moveAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getPercentMoveComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = moveAverage < criteria.getPercentMoveComparison();
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
