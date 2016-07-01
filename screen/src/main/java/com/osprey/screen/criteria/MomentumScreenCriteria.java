package com.osprey.screen.criteria;

public class MomentumScreenCriteria implements IStockScreenCriteria {

	private StockScreenType type;
	private double price;
	private RelationalOperator relationalOperator;

	public MomentumScreenCriteria() {

	}

	public MomentumScreenCriteria(double price, RelationalOperator operator) {
		type = StockScreenType.MOMENTUM_X;
		this.price = price;
		this.relationalOperator = operator;
	}

	public StockScreenType getType() {
		return type;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

	public double getPrice() {
		return price;
	}

}
