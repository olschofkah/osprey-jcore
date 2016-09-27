package com.osprey.screen.screens;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.CamarillaBandCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class CamarillaBandScreen extends NumericalRelationalComparisonStockScreen {

	private final CamarillaBandCriteria criteria;

	public CamarillaBandScreen(CamarillaBandCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double[] camarillaBands = OspreyQuantMath.camarillaBands(criteria.getPeriod(), 1, sqc.getHistoricalQuotes());

		// this is populated with the current quote for market hours ...
		// with 'close' being last.
		compare(sqc.getHistoricalQuotes().get(0).getClose(), camarillaBands[criteria.getBandIndex()]);

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}

}
