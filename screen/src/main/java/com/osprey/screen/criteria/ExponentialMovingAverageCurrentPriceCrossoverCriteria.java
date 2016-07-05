package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.screen.criteria.constants.ScreenType;

public class ExponentialMovingAverageCurrentPriceCrossoverCriteria implements IScreenCriteria {

	private ScreenType type;
	private int period1;
	private int range;

	private CrossDirection direction;

	public ExponentialMovingAverageCurrentPriceCrossoverCriteria() {

	}

	public ExponentialMovingAverageCurrentPriceCrossoverCriteria(int period1, int range,
			CrossDirection direction) {
		this.type = ScreenType.EMA_X_PRICE;
		this.period1 = period1;
		this.range = range;
		this.direction = direction;
	}

	public ScreenType getType() {
		return type;
	}

	public int getPeriod1() {
		return period1;
	}

	public int getRange() {
		return range;
	}

	public CrossDirection getDirection() {
		return direction;
	}

}
