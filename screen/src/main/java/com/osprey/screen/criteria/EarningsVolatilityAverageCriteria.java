package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class EarningsVolatilityAverageCriteria implements IScreenCriteria {

	private ScreenType type;
	private double volatilityComparison;
	private int daysBefore;
	private int daysAfter;
	private RelationalOperator relationalOperator;

	public EarningsVolatilityAverageCriteria() {
	}

	public EarningsVolatilityAverageCriteria(int daysBefore, int daysAfter, double volatilityComparison,
			RelationalOperator relationalOperator) {
		type = ScreenType.EARNINGS_VOLATILITY;
		this.daysBefore = daysBefore;
		this.daysAfter = daysAfter;
		this.volatilityComparison = volatilityComparison;
		this.relationalOperator = relationalOperator;
	}

	public ScreenType getType() {
		return type;
	}

	public double getVolatilityComparison() {
		return volatilityComparison;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

	public int getDaysAfter() {
		return daysAfter;
	}

	public int getDaysBefore() {
		return daysBefore;
	}
}
