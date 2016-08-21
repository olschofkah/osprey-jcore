package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.screen.criteria.constants.ScreenType;

public class StochasticFullOscillatorSignalCrossCriteria implements IScreenCriteria {

	private ScreenType type;
	private int periodLookBack;
	private int periodK;
	private int periodD;
	private int range;
	private CrossDirection direction;

	public StochasticFullOscillatorSignalCrossCriteria() {

	}

	public StochasticFullOscillatorSignalCrossCriteria(int periodLookBack, int periodK, int periodD, int range,
			CrossDirection direction) {
		this.type = ScreenType.STOCHASTICS_FULL_SIGNAL_CROSS;
		this.periodLookBack = periodLookBack;
		this.periodK = periodK;
		this.periodD = periodD;
		this.range = range;
		this.direction = direction;
	}

	public ScreenType getType() {
		return type;
	}

	public int getPeriodLookBack() {
		return periodLookBack;
	}

	public int getPeriodK() {
		return periodK;
	}

	public int getPeriodD() {
		return periodD;
	}

	public int getRange() {
		return range;
	}

	public CrossDirection getDirection() {
		return direction;
	}

}
