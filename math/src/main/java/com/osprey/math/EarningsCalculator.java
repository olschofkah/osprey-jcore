package com.osprey.math;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityEvent;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.SecurityEventType;

public class EarningsCalculator {

	public static double calcEarningsPercentMove(SecurityQuoteContainer sqc, int daysBefore, int daysAfter) {

		if (sqc.getEvents() == null) {
			return -1;
		}

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
			return -1;
		}

		int daysBeforePlusOne = daysBefore + 1;
		int daysAfterPlusOne = daysAfter + 1;

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
		return moveAverage;
	}

	public static double calcEarningsVolatility(SecurityQuoteContainer sqc, int daysBefore, int daysAfter) {

		if (sqc.getEvents() == null) {
			return -1;
		}

		int period = daysAfter + daysBefore + 1;

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
			return -1;
		}

		List<HistoricalQuote> currentQuotePeriodList = new ArrayList<>(period);

		int daysBeforePlusOne = daysBefore + 1;
		int daysAfterPlusOne = daysAfter + 1;

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

		return volatilityAverage;
	}

}
