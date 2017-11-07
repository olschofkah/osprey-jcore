package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class VolatilityCriteria implements IScreenCriteria {

	private ScreenType type;
	private int period; // period in days
	private double volatilityComparison;
	private RelationalOperator relationalOperator;
	
	public VolatilityCriteria(){}

	public VolatilityCriteria(int period, double volatilityComparison, RelationalOperator relationalOperator) {
		type = ScreenType.VOLATILITY;
		this.period = period;
		this.volatilityComparison = volatilityComparison;
		this.relationalOperator = relationalOperator;
	}

	public ScreenType getType() {
		return type;
	}

	public int getPeriod() {
		return period;
	}

	public double getVolatilityComparison() {
		return volatilityComparison;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

}
