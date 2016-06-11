package com.osprey.screen.criteria;

public class ExponentialMovingAverageCriteria implements IStockScreenCriteria {

	private final StockScreenType type;
	private final int d1;
	private final int d2;
	private final RelationalOperator operator;

	public ExponentialMovingAverageCriteria(int d1, int d2, RelationalOperator operator) {
		type = StockScreenType.EMA;
		this.d1 = d1;
		this.d2 = d2;
		this.operator = operator;
	}

	public StockScreenType getType() {
		return type;
	}

	public int getD1() {
		return d1;
	}

	public int getD2() {
		return d2;
	}

	public RelationalOperator getOperator() {
		return operator;
	}

}
