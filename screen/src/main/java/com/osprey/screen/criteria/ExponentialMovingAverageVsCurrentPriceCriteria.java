package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class ExponentialMovingAverageVsCurrentPriceCriteria implements IScreenCriteria {

	private ScreenType type;
	private int period1;
	private RelationalOperator relationalOperator;

	public ExponentialMovingAverageVsCurrentPriceCriteria() {

	}

	public ExponentialMovingAverageVsCurrentPriceCriteria(int period1, RelationalOperator operator) {
		type = ScreenType.EMA_VS_PRICE;
		this.period1 = period1;
		this.relationalOperator = operator;
	}

	public ScreenType getType() {
		return type;
	}

	public int getPeriod1() {
		return period1;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

}
