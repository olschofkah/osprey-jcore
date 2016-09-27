package com.osprey.screen.screens;

import com.osprey.screen.criteria.BetaCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class BetaScreen extends NumericalRelationalComparisonStockScreen {

	private final BetaCriteria criteria;

	public BetaScreen(BetaCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double beta = sqc.getFundamentalQuote().getBeta();

		compare(beta, criteria.getBetaComparison());

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}

}
