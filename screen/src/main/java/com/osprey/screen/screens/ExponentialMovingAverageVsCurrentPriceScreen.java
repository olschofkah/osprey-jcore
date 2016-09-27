package com.osprey.screen.screens;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.criteria.ExponentialMovingAverageVsCurrentPriceCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class ExponentialMovingAverageVsCurrentPriceScreen extends NumericalRelationalComparisonStockScreen {

	private final ExponentialMovingAverageVsCurrentPriceCriteria criteria;

	public ExponentialMovingAverageVsCurrentPriceScreen(ExponentialMovingAverageVsCurrentPriceCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		if (criteria.getPeriod1() - 1 >= sqc.getHistoricalQuotes().size()) {
			throw new InsufficientHistoryException();
		}

		double ema1 = OspreyQuantMath.ema(criteria.getPeriod1(), 0, sqc.getHistoricalQuotes());

		double close = sqc.getHistoricalQuotes().get(0).getAdjClose();

		compare(ema1, close);

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
