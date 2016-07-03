package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class VolumeAverageCriteria implements IScreenCriteria {

	private ScreenType type;
	private long benchmarkVolume;
	private int period;
	private RelationalOperator relationalOperator;

	public VolumeAverageCriteria() {

	}

	public VolumeAverageCriteria(long benchmarkVolume, int period, RelationalOperator operator) {
		type = ScreenType.VOLUME_AVG;
		this.benchmarkVolume = benchmarkVolume;
		this.period = period;
		this.relationalOperator = operator;
	}

	public ScreenType getType() {
		return type;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

	public long getBenchmarkVolume() {
		return benchmarkVolume;
	}

	public int getPeriod() {
		return period;
	}

}
