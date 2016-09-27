package com.osprey.screen.screens;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.RelativeStrengthIndexCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class RelativeStrengthIndexScreen extends NumericalRelationalComparisonStockScreen {

	private final RelativeStrengthIndexCriteria criteria;

	public RelativeStrengthIndexScreen(RelativeStrengthIndexCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double rsi;
		for (int i = 0; i < criteria.getPeriodRange(); ++i) {

			rsi = OspreyQuantMath.rsiUsingWilders(criteria.getPeriod(), i, sqc.getHistoricalQuotes());

			compare(rsi, criteria.getRsiComparison());

			if (passed) {
				break;
			}
		}

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
