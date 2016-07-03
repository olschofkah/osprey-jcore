package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class SimpleMovingAverageCriteria implements IStockScreenCriteria {

	private ScreenType type;
	private int period1;
	private int period2;
	private RelationalOperator relationalOperator;

	public SimpleMovingAverageCriteria() {

	}

	public SimpleMovingAverageCriteria(int period1, int period2, RelationalOperator operator) {
		type = ScreenType.SMA;
		this.period1 = period1;
		this.period2 = period2;
		this.relationalOperator = operator;
	}

	public ScreenType getType() {
		return type;
	}

	public int getPeriod1() {
		return period1;
	}

	public int getPeriod2() {
		return period2;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

}
