package com.osprey.screen.screens;

import com.osprey.math.EarningsCalculator;
import com.osprey.screen.criteria.EarningsPercentMoveAverageCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class EarningsPercentMoveAverageScreen extends NumericalRelationalComparisonStockScreen {

	private final EarningsPercentMoveAverageCriteria criteria;

	public EarningsPercentMoveAverageScreen(EarningsPercentMoveAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double moveAverage = EarningsCalculator.calcEarningsPercentMove(sqc, criteria.getDaysBefore(),
				criteria.getDaysAfter());

		if (moveAverage <= 0) {
			passed = false;
			return this;
		}

		compare(moveAverage, criteria.getPercentMoveComparison());

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
