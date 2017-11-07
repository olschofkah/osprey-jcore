package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.GapDirection;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class PriceGapCriteria implements IScreenCriteria {

	private ScreenType type;
	private double percentGap;
	private GapDirection direction;
	private RelationalOperator relationalOperator;

	public PriceGapCriteria() {

	}

	public PriceGapCriteria(double percentGap, GapDirection direction, RelationalOperator operator) {
		type = ScreenType.GAP_UP_DOWN;
		this.percentGap = percentGap;
		this.direction = direction;
		this.relationalOperator = operator;
	}

	public ScreenType getType() {
		return type;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

	public double getPercentGap() {
		return percentGap;
	}

	public GapDirection getDirection() {
		return direction;
	}

}
