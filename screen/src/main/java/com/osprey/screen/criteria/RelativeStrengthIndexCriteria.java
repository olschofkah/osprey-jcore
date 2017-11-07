package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

/**
 * RSI
 * 
 * @author Goliaeth
 *
 */
public class RelativeStrengthIndexCriteria implements IScreenCriteria {

	private ScreenType type;
	private int period; // period in days
	private int periodRange;
	private double rsiComparison;
	private RelationalOperator relationalOperator;

	public RelativeStrengthIndexCriteria() {
	}

	public RelativeStrengthIndexCriteria(int period, int periodRange, double rsiComparison,
			RelationalOperator relationalOperator) {
		type = ScreenType.RSI;
		this.period = period;
		this.rsiComparison = rsiComparison;
		this.relationalOperator = relationalOperator;
		this.periodRange = periodRange;
	}

	public ScreenType getType() {
		return type;
	}

	public int getPeriod() {
		return period;
	}

	public double getRsiComparison() {
		return rsiComparison;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

	public int getPeriodRange() {
		return periodRange;
	}

}
