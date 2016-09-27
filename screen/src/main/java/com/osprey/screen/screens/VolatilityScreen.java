package com.osprey.screen.screens;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.VolatilityCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class VolatilityScreen extends NumericalRelationalComparisonStockScreen {

	private final VolatilityCriteria criteria;

	public VolatilityScreen(VolatilityCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double volatility = OspreyQuantMath.volatility(criteria.getPeriod(), sqc.getHistoricalQuotes());

		compare(volatility, criteria.getVolatilityComparison());

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}

}
