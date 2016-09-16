package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.screen.criteria.constants.ScreenType;

public class MoneyFlowIndexLevelCrossCriteria implements IScreenCriteria {

	private ScreenType type;
	private int period;
	private int range;
	private int level;
	private CrossDirection direction;

	public MoneyFlowIndexLevelCrossCriteria() {

	}

	public MoneyFlowIndexLevelCrossCriteria(int period, int range, int level, CrossDirection direction) {
		this.type = ScreenType.MFI_X;
		this.period = period;
		this.range = range;
		this.level = level;
		this.direction = direction;
	}

	public ScreenType getType() {
		return type;
	}

	public int getPeriod() {
		return period;
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
