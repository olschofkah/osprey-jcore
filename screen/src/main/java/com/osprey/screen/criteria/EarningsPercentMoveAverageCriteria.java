package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class EarningsPercentMoveAverageCriteria implements IScreenCriteria {

	private ScreenType type;
	private double percentMoveComparison;
	private int daysBefore;
	private int daysAfter;
	private RelationalOperator relationalOperator;

	public EarningsPercentMoveAverageCriteria() {
	}

	public EarningsPercentMoveAverageCriteria(int daysBefore, int daysAfter, double percentMoveComparison,
			RelationalOperator relationalOperator) {
		type = ScreenType.EARNINGS_PCT_MOVE;
		this.daysBefore = daysBefore;
		this.daysAfter = daysAfter;
		this.percentMoveComparison = percentMoveComparison;
		this.relationalOperator = relationalOperator;
	}

	public ScreenType getType() {
		return type;
	}

	public double getPercentMoveComparison() {
		return percentMoveComparison;
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
