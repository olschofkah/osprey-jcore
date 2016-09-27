package com.osprey.screen.screens;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria._52WeekRangePercentageCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class _52WeekRangePercentageScreen extends NumericalRelationalComparisonStockScreen {

	private final _52WeekRangePercentageCriteria criteria;

	public _52WeekRangePercentageScreen(_52WeekRangePercentageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double pct = OspreyQuantMath.percentIn52Week(sqc);

		compare(pct, criteria.getPercent());

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
