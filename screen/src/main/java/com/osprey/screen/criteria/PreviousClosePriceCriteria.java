package com.osprey.screen.criteria;

public class PreviousClosePriceCriteria implements IStockScreenCriteria {

	private StockScreenType type;
	private double price;
	private RelationalOperator relationalOperator;

	public PreviousClosePriceCriteria() {

	}

	public PreviousClosePriceCriteria(double price, RelationalOperator operator) {
		type = StockScreenType.PRICE;
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
