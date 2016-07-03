package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class VolumeAverageComparisonCriteria implements IStockScreenCriteria {

	private ScreenType type;
	private int period1;
	private int period2;
	private RelationalOperator relationalOperator;

	public VolumeAverageComparisonCriteria() {

	}

	public VolumeAverageComparisonCriteria(int period1, int period2, RelationalOperator operator) {
		type = ScreenType.VOLUME_COMPARISON;
		this.period1 = period1;
		this.period2 = period2;
		this.relationalOperator = operator;
	}

	public ScreenType getType() {
		return type;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

	public int getPeriod1() {
		return period1;
	}

	public int getPeriod2() {
		return period2;
	}

}
