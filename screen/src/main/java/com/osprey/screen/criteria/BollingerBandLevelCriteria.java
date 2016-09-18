package com.osprey.screen.criteria;

import com.osprey.math.result.BollingerBand;
import com.osprey.math.result.MovingAverageType;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class BollingerBandLevelCriteria implements IScreenCriteria {

	private ScreenType type;
	private int period;
	private double multiplier;
	private MovingAverageType maType;
	private BollingerBand band;
	private int range;
	private RelationalOperator relationalOperator;

	public BollingerBandLevelCriteria() {

	}

	public BollingerBandLevelCriteria(int period, double multiplier, MovingAverageType maType, BollingerBand band,
			int range, RelationalOperator relationalOperator) {
		this.type = ScreenType.BOLLINGER_BAND_LEVEL;
		this.period = period;
		this.multiplier = multiplier;
		this.maType = maType;
		this.band = band;
		this.range = range;
		this.relationalOperator = relationalOperator;
	}

	public ScreenType getType() {
		return type;
	}

	public int getRange() {
		return range;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

	public int getPeriod() {
		return period;
	}

	public double getMultiplier() {
		return multiplier;
	}
	
	public BollingerBand getBand(){
		return band;
	}

	public MovingAverageType getMaType() {
		return maType;
	}

}
