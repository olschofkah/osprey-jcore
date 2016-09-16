package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class MoneyFlowIndexLevelCriteria implements IScreenCriteria {

	private ScreenType type;
	private int period;
	private int range;
	private int level;
	private RelationalOperator relationalOperator;

	public MoneyFlowIndexLevelCriteria() {

	}

	public MoneyFlowIndexLevelCriteria(int period, int range, int level, RelationalOperator relationalOperator) {
		this.type = ScreenType.MFI;
		this.period = period;
		this.range = range;
		this.level = level;
		this.relationalOperator = relationalOperator;
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

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

}
