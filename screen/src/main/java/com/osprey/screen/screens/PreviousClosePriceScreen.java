package com.osprey.screen.screens;

import com.osprey.screen.criteria.PreviousClosePriceCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class PreviousClosePriceScreen extends NumericalRelationalComparisonStockScreen {

	private final PreviousClosePriceCriteria criteria;

	public PreviousClosePriceScreen(PreviousClosePriceCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		compare(sqc.getSecurity().getPreviousClose(), criteria.getPrice());

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
