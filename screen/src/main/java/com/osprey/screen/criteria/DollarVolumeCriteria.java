package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class DollarVolumeCriteria implements IScreenCriteria {

	private ScreenType type;
	private double dollarVolumeComparison;
	private int range;
	private RelationalOperator relationalOperator;

	public DollarVolumeCriteria() {

	}

	public DollarVolumeCriteria(double dollarVolumeComparison, int range, RelationalOperator relationalOperator) {
		type = ScreenType.DOLLAR_VOLUME;
		this.relationalOperator = relationalOperator;
		this.dollarVolumeComparison = dollarVolumeComparison;
		this.range = range;
	}

	public ScreenType getType() {
		return type;
	}

	public double getDollarVolumeComparison() {
		return dollarVolumeComparison;
	}

	public int getRange() {
		return range;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

}
