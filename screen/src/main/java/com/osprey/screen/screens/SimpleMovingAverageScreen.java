package com.osprey.screen.screens;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.result.SMAPair;
import com.osprey.screen.criteria.SimpleMovingAverageCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class SimpleMovingAverageScreen extends NumericalRelationalComparisonStockScreen {

	private final SimpleMovingAverageCriteria criteria;

	public SimpleMovingAverageScreen(SimpleMovingAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		SMAPair smaPair = OspreyQuantMath.smaPair(criteria.getPeriod1(), criteria.getPeriod2(), 0,
				sqc.getHistoricalQuotes());
		double sma1 = smaPair.getSma1();
		double sma2 = smaPair.getSma2();

		compare(sma1, sma2);

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
