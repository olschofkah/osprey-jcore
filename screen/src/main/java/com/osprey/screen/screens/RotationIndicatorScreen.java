package com.osprey.screen.screens;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.RotationIndicatorCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class RotationIndicatorScreen extends NumericalRelationalComparisonStockScreen {

	private final RotationIndicatorCriteria criteria;

	public RotationIndicatorScreen(RotationIndicatorCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double ri = OspreyQuantMath.rotationIndicator(criteria.getLongPeriod(), criteria.getShortPeriod(), 0,
				criteria.getLag(), sqc.getHistoricalQuotes());

		compare(ri, criteria.getRotationBenchmark());

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
