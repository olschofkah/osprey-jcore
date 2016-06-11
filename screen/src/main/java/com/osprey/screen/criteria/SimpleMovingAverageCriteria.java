package com.osprey.screen.criteria;

public class SimpleMovingAverageCriteria implements IStockScreenCriteria {

	private final StockScreenType type;
	private final int p1;
	private final int p2;
	private final RelationalOperator operator;

	public SimpleMovingAverageCriteria(int d1, int d2, RelationalOperator operator) {
		type = StockScreenType.SMA;
		this.p1 = d1;
		this.p2 = d2;
		this.operator = operator;
	}

	public StockScreenType getType() {
		return type;
	}

	public int getP1() {
		return p1;
	}

	public int getP2() {
		return p2;
	}

	public RelationalOperator getOperator() {
		return operator;
	}

}
