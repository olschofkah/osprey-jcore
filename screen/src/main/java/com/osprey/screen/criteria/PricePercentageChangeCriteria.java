package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class PricePercentageChangeCriteria implements IStockScreenCriteria {

	private ScreenType type;
	private double percentChange;
	private int period;
	private RelationalOperator relationalOperator;

	public PricePercentageChangeCriteria() {

	}

	public PricePercentageChangeCriteria(double percentChange, int period, RelationalOperator operator) {
		type = ScreenType.PRICE_CHANGE;
		this.percentChange = percentChange;
		this.period = period;
		this.relationalOperator = operator;
	}

	public ScreenType getType() {
		return type;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

	public double getPercentChange() {
		return percentChange;
	}

	public int getPeriod() {
		return period;
	}

}
