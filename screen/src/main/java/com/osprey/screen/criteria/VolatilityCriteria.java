package com.osprey.screen.criteria;

public class VolatilityCriteria implements IStockScreenCriteria {

	private final StockScreenType type;
	private final int period; // period in days
	private final double volatilityComparison;
	private final RelationalOperator operator;

	public VolatilityCriteria(int period, double volatilityComparison, RelationalOperator operator) {
		type = StockScreenType.VOLATILITY;
		this.period = period;
		this.volatilityComparison = volatilityComparison;
		this.operator = operator;
	}

	public StockScreenType getType() {
		return type;
	}

	public int getPeriod() {
		return period;
	}

	public double getVolatilityComparison() {
		return volatilityComparison;
	}

	public RelationalOperator getOperator() {
		return operator;
	}

}
