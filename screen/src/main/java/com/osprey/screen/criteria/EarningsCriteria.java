package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class EarningsCriteria implements IStockScreenCriteria {

	private ScreenType type;
	private int daysAway;
	private RelationalOperator relationalOperator;

	public EarningsCriteria() {

	}

	public EarningsCriteria(int daysAway, RelationalOperator relationalOperator) {
		type = ScreenType.EARNINGS_ANNOUNCEMENT;
		this.relationalOperator = relationalOperator;
		this.daysAway = daysAway;
	}

	public ScreenType getType() {
		return type;
	}

	public int getDaysAway() {
		return daysAway;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

}
