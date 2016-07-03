package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class _52WeekRangePercentageCriteria implements IScreenCriteria {

	private ScreenType type;
	private double percent;
	private RelationalOperator relationalOperator;

	public _52WeekRangePercentageCriteria() {

	}

	public _52WeekRangePercentageCriteria(double percent, RelationalOperator operator) {
		type = ScreenType._52_PCT;
		this.percent = percent;
		this.relationalOperator = operator;
	}

	public ScreenType getType() {
		return type;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

	public double getPercent() {
		return percent;
	}

}
