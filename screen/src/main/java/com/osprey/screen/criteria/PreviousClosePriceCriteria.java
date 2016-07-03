package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class PreviousClosePriceCriteria implements IScreenCriteria {

	private ScreenType type;
	private double price;
	private RelationalOperator relationalOperator;

	public PreviousClosePriceCriteria() {

	}

	public PreviousClosePriceCriteria(double price, RelationalOperator operator) {
		type = ScreenType.PRICE;
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
