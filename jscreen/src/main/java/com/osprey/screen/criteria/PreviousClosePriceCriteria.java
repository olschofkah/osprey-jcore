package com.osprey.screen.criteria;

public class PreviousClosePriceCriteria implements IStockScreenCriteria {

	private final StockScreenType type;
	private final double price;
	private final RelationalOperator operator;

	public PreviousClosePriceCriteria(double price, RelationalOperator operator) {
		type = StockScreenType.PRICE;
		this.price = price;
		this.operator = operator;
	}

	public StockScreenType getType() {
		return type;
	}

	public RelationalOperator getOperator() {
		return operator;
	}

	public double getPrice() {
		return price;
	}

}
