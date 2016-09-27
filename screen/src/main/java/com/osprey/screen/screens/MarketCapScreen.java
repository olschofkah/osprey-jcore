package com.osprey.screen.screens;

import com.osprey.screen.criteria.MarketCapCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class MarketCapScreen extends NumericalRelationalComparisonStockScreen {

	private final MarketCapCriteria criteria;

	public MarketCapScreen(MarketCapCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		compare(sqc.getFundamentalQuote().getMarketCap(), criteria.getMarketCapComparison());

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
