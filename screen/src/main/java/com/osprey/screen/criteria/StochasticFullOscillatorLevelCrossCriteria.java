package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.screen.criteria.constants.ScreenType;

public class StochasticFullOscillatorLevelCrossCriteria implements IScreenCriteria {

	private ScreenType type;
	private int periodLookBack;
	private int periodK;
	private int periodD;
	private int range;
	private int level;
	private CrossDirection direction;

	public StochasticFullOscillatorLevelCrossCriteria() {

	}

	public StochasticFullOscillatorLevelCrossCriteria(int periodLookBack, int periodK, int periodD, int range,
			int level, CrossDirection direction) {
		this.type = ScreenType.STOCHASTICS_FULL_LEVEL_CROSS;
		this.periodLookBack = periodLookBack;
		this.periodK = periodK;
		this.periodD = periodD;
		this.range = range;
		this.level = level;
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

	public int getLevel() {
		return level;
	}

	public CrossDirection getDirection() {
		return direction;
	}

}
