package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.screen.criteria.constants.ScreenType;

public class MovingAverageConverganceDiverganceCrossoverCriteria implements IScreenCriteria {

	private ScreenType type;
	private int fastPeriod;
	private int slowPeriod;
	private int signalPeriod;
	private int range;
	private CrossDirection direction;

	public MovingAverageConverganceDiverganceCrossoverCriteria() {

	}

	public MovingAverageConverganceDiverganceCrossoverCriteria(int fastPeriod, int slowPeriod, int signalPeriod,
			int range, CrossDirection direction) {
		this.type = ScreenType.MACD;
		this.fastPeriod = fastPeriod;
		this.slowPeriod = slowPeriod;
		this.signalPeriod = signalPeriod;
		this.range = range;
		this.direction = direction;
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

	public CrossDirection getDirection() {
		return direction;
	}

	public void setDirection(CrossDirection direction) {
		this.direction = direction;
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

}
