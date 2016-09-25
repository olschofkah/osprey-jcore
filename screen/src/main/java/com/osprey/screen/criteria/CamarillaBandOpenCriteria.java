package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class CamarillaBandOpenCriteria implements IScreenCriteria {

	private ScreenType type;
	private int period;
	private int bandIndex1;
	private int bandIndex2;
	private RelationalOperator relationalOperator1;
	private RelationalOperator relationalOperator2;

	public CamarillaBandOpenCriteria() {
	}

	public CamarillaBandOpenCriteria(int period, int bandIndex1, RelationalOperator relationalOperator1,
			int bandIndex2, RelationalOperator relationalOperator2) {
		type = ScreenType.CAMARILLA_BAND_OPEN;
		this.period = period;
		this.bandIndex1 = bandIndex1;
		this.relationalOperator1 = relationalOperator1;
		this.bandIndex2 = bandIndex2;
		this.relationalOperator2 = relationalOperator2;
	}

	public ScreenType getType() {
		return type;
	}

	public int getPeriod() {
		return period;
	}

	public int getBandIndex1() {
		return bandIndex1;
	}

	public RelationalOperator getRelationalOperator1() {
		return relationalOperator1;
	}

	public int getBandIndex2() {
		return bandIndex2;
	}

	public RelationalOperator getRelationalOperator2() {
		return relationalOperator2;
	}

}
