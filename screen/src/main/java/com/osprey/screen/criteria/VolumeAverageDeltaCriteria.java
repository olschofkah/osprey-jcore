package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class VolumeAverageDeltaCriteria implements IScreenCriteria {

	private ScreenType type;
	private double deltaPercent;
	private int period;
	private int range;
	private RelationalOperator relationalOperator;

	public VolumeAverageDeltaCriteria() {

	}

	public VolumeAverageDeltaCriteria(double deltaPercent, int period, int range, RelationalOperator operator) {
		type = ScreenType.VOLUME_AVG_DELTA;
		this.deltaPercent = deltaPercent;
		this.period = period;
		this.range = range;
		this.relationalOperator = operator;
	}

	public ScreenType getType() {
		return type;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

	public double getDeltaPercent() {
		return deltaPercent;
	}

	public int getRange() {
		return range;
	}

	public int getPeriod() {
		return period;
	}

}
