package com.osprey.screen.criteria;

public class SimpleMovingAverageCriteria implements IStockScreenCriteria {

	private StockScreenType type;
	private int period1;
	private int period2;
	private RelationalOperator relationalOperator;

	public SimpleMovingAverageCriteria() {

	}

	public SimpleMovingAverageCriteria(int period1, int period2, RelationalOperator operator) {
		type = StockScreenType.SMA;
		this.period1 = period1;
		this.period2 = period2;
		this.relationalOperator = operator;
	}

	public StockScreenType getType() {
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
