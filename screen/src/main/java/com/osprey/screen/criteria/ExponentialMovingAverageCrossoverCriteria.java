package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.screen.criteria.constants.ScreenType;

public class ExponentialMovingAverageCrossoverCriteria implements IScreenCriteria {

	private ScreenType type;
	private int period1;
	private int period2;
	private int range;
	private CrossDirection direction;

	public ExponentialMovingAverageCrossoverCriteria() {

	}

	public ExponentialMovingAverageCrossoverCriteria(int period1, int period2, int range,
			CrossDirection direction) {
		this.type = ScreenType.EMA_X;
		this.period1 = period1;
		this.period2 = period2;
		this.range = range;
		this.direction = direction;
	}

	public ScreenType getType() {
		return type;
	}

	public int getPeriod1() {
		return period1;
	}

	public int getPeriod2() {
		return period2;
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

}
