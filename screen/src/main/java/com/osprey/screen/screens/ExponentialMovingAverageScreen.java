package com.osprey.screen.screens;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.criteria.ExponentialMovingAverageCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class ExponentialMovingAverageScreen extends NumericalRelationalComparisonStockScreen {

	private final ExponentialMovingAverageCriteria criteria;

	public ExponentialMovingAverageScreen(ExponentialMovingAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		if (criteria.getPeriod1() - 1 >= sqc.getHistoricalQuotes().size()
				|| criteria.getPeriod2() - 1 >= sqc.getHistoricalQuotes().size()) {
			throw new InsufficientHistoryException();
		}

		double ema1 = OspreyQuantMath.ema(criteria.getPeriod1(), 0, sqc.getHistoricalQuotes());
		double ema2 = OspreyQuantMath.ema(criteria.getPeriod2(), 0, sqc.getHistoricalQuotes());

		compare(ema1, ema2);

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
