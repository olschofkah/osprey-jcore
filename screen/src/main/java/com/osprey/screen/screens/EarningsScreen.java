package com.osprey.screen.screens;

import java.time.LocalDate;

import com.osprey.screen.criteria.EarningsCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class EarningsScreen implements IStockScreen {

	private final EarningsCriteria criteria;
	private boolean passed;

	public EarningsScreen(EarningsCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		if (sqc.getUpcomingEvents() == null || sqc.getUpcomingEvents().getNextEarningsDateEstLow() == null) {
			passed = false;
			return this;
		}

		LocalDate nextEarningsDateEstLow = sqc.getUpcomingEvents().getNextEarningsDateEstLow();
		LocalDate date = LocalDate.now().plusDays(criteria.getDaysAway());

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = nextEarningsDateEstLow.isEqual(date);
			break;
		case _GE:
			passed = nextEarningsDateEstLow.isAfter(date) || nextEarningsDateEstLow.isEqual(date);
			break;
		case _GT:
			passed = nextEarningsDateEstLow.isAfter(date);
			break;
		case _LE:
			passed = nextEarningsDateEstLow.isBefore(date) || nextEarningsDateEstLow.isEqual(date);
			break;
		case _LT:
			passed = nextEarningsDateEstLow.isBefore(date);
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
