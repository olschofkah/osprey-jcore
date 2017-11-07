package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class MarketCapCriteria implements IScreenCriteria {

	private ScreenType type;
	private long marketCapComparison;
	private RelationalOperator relationalOperator;

	public MarketCapCriteria() {

	}

	public MarketCapCriteria(long marketCapComparison, RelationalOperator relationalOperator) {
		type = ScreenType.MARKET_CAP;
		this.relationalOperator = relationalOperator;
		this.marketCapComparison = marketCapComparison;
	}

	public ScreenType getType() {
		return type;
	}

	public long getMarketCapComparison() {
		return marketCapComparison;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

}
