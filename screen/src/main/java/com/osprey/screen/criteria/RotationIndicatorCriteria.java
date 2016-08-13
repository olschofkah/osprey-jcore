package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class RotationIndicatorCriteria implements IScreenCriteria {

	private ScreenType type;
	private int shortPeriod;
	private int longPeriod;
	private int lag;
	private double rotationBenchmark;
	private RelationalOperator relationalOperator;

	public RotationIndicatorCriteria() {
	}

	public RotationIndicatorCriteria(int longPeriod, int shortPeriod, int lag, double rotationBenchmark,
			RelationalOperator relationalOperator) {
		type = ScreenType.ROTATION_INDICATOR;
		this.shortPeriod = shortPeriod;
		this.longPeriod = longPeriod;
		this.lag = lag;
		this.rotationBenchmark = rotationBenchmark;
		this.relationalOperator = relationalOperator;
	}

	public ScreenType getType() {
		return type;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

	public int getShortPeriod() {
		return shortPeriod;
	}

	public int getLongPeriod() {
		return longPeriod;
	}

	public double getRotationBenchmark() {
		return rotationBenchmark;
	}

	public int getLag() {
		return lag;
	}
}
