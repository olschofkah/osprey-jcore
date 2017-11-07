package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class VolumeCriteria implements IScreenCriteria {

	private ScreenType type;
	private double volumeComparison;
	private int range;
	private RelationalOperator relationalOperator;

	public VolumeCriteria() {

	}

	public VolumeCriteria(double volumeComparison, int range, RelationalOperator relationalOperator) {
		type = ScreenType.VOLUME;
		this.relationalOperator = relationalOperator;
		this.volumeComparison = volumeComparison;
		this.range = range;
	}

	public ScreenType getType() {
		return type;
	}

	public double getVolumeComparison() {
		return volumeComparison;
	}

	public int getRange() {
		return range;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

}
