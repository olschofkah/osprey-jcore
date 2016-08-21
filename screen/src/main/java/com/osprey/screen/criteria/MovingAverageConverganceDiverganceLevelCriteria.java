package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class MovingAverageConverganceDiverganceLevelCriteria implements IScreenCriteria {

	private ScreenType type;
	private int fastPeriod;
	private int slowPeriod;
	private int signalPeriod;
	private int range;
	private double level;
	private RelationalOperator relationalOperator;

	public MovingAverageConverganceDiverganceLevelCriteria() {

	}

	public MovingAverageConverganceDiverganceLevelCriteria(int fastPeriod, int slowPeriod, int signalPeriod,
			int range, double level, RelationalOperator relationalOperator) {
		this.type = ScreenType.MACD_LEVEL;
		this.fastPeriod = fastPeriod;
		this.slowPeriod = slowPeriod;
		this.signalPeriod = signalPeriod;
		this.range = range;
		this.level = level;
		this.relationalOperator = relationalOperator;
	}

	public ScreenType getType() {
		return type;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public double getLevel() {
		return level;
	}

	public int getFastPeriod() {
		return fastPeriod;
	}

	public int getSlowPeriod() {
		return slowPeriod;
	}

	public int getSignalPeriod() {
		return signalPeriod;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

}
