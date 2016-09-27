package com.osprey.screen.screens;

import com.osprey.math.EarningsCalculator;
import com.osprey.screen.criteria.EarningsVolatilityAverageCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class EarningsVolatilityAverageScreen extends NumericalRelationalComparisonStockScreen {

	private final EarningsVolatilityAverageCriteria criteria;

	public EarningsVolatilityAverageScreen(EarningsVolatilityAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double volatilityAverage = EarningsCalculator.calcEarningsVolatility(sqc, criteria.getDaysBefore(),
				criteria.getDaysAfter());

		if (volatilityAverage <= 0) {
			passed = false;
			return this;
		}

		compare(volatilityAverage, criteria.getVolatilityComparison());

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
