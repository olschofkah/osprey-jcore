package com.osprey.screen.criteria;

import com.osprey.math.result.BollingerBand;
import com.osprey.math.result.MovingAverageType;
import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.screen.criteria.constants.ScreenType;

public class BollingerBandCrossCriteria implements IScreenCriteria {

	private ScreenType type;
	private int period;
	private double multiplier;
	private MovingAverageType maType;
	private BollingerBand band;
	private int range;
	private CrossDirection direction;

	public BollingerBandCrossCriteria() {

	}

	public BollingerBandCrossCriteria(int period, double multiplier, MovingAverageType maType, BollingerBand band,
			int range, CrossDirection direction) {
		this.type = ScreenType.BOLLINGER_BAND_X;
		this.period = period;
		this.multiplier = multiplier;
		this.maType = maType;
		this.band = band;
		this.range = range;
		this.direction = direction;
	}

	public ScreenType getType() {
		return type;
	}

	public int getRange() {
		return range;
	}

	public int getPeriod() {
		return period;
	}

	public double getMultiplier() {
		return multiplier;
	}

	public BollingerBand getBand() {
		return band;
	}

	public MovingAverageType getMaType() {
		return maType;
	}

	public CrossDirection getDirection() {
		return direction;
	}

}
