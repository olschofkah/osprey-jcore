package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class StochasticFullOscillatorLevelCriteria implements IScreenCriteria {

	private ScreenType type;
	private int periodLookBack;
	private int periodK;
	private int periodD;
	private int range;
	private int level;
	private RelationalOperator relationalOperator;

	public StochasticFullOscillatorLevelCriteria() {

	}

	public StochasticFullOscillatorLevelCriteria(int periodLookBack, int periodK, int periodD, int range, int level,
			RelationalOperator relationalOperator) {
		this.type = ScreenType.STOCHASTICS_FULL_LEVEL;
		this.periodLookBack = periodLookBack;
		this.periodK = periodK;
		this.periodD = periodD;
		this.range = range;
		this.level = level;
		this.relationalOperator = relationalOperator;
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

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

}
