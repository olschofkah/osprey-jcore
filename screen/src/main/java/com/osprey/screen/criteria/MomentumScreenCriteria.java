package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class MomentumScreenCriteria implements IStockScreenCriteria {

	private ScreenType type;
	private double price;
	private RelationalOperator relationalOperator;

	public MomentumScreenCriteria() {

	}

	public MomentumScreenCriteria(double price, RelationalOperator operator) {
		type = ScreenType.MOMENTUM_X;
		this.price = price;
		this.relationalOperator = operator;
	}

	public ScreenType getType() {
		return type;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

	public double getPrice() {
		return price;
	}

}
