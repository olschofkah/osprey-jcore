package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class CamarillaBandCriteria implements IScreenCriteria {

	private ScreenType type;
	private int period;
	private int bandIndex;
	private RelationalOperator relationalOperator;

	public CamarillaBandCriteria() {
	}

	public CamarillaBandCriteria(int period, int bandIndex, RelationalOperator relationalOperator) {
		type = ScreenType.CAMARILLA_BAND;
		this.period = period;
		this.bandIndex = bandIndex;
		this.relationalOperator = relationalOperator;
	}

	public ScreenType getType() {
		return type;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

	public int getPeriod() {
		return period;
	}

	public int getBandIndex() {
		return bandIndex;
	}

}
