package com.osprey.screen.screens;

import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.criteria.PriceGapCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class PriceGapScreen extends NumericalRelationalComparisonStockScreen {

	private final PriceGapCriteria criteria;

	public PriceGapScreen(PriceGapCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		if (sqc.getHistoricalQuotes().size() < 2) {
			throw new InsufficientHistoryException();
		}

		double p0 = sqc.getHistoricalQuotes().get(0).getOpen();
		double p1 = sqc.getHistoricalQuotes().get(1).getAdjClose();

		double pctGap = 1 - (p0 / p1);

		compare(pctGap, criteria.getPercentGap());

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
