package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.BandSelection;
import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.screen.criteria.constants.ScreenType;

public class ExponentialMovingAverageBandCrossoverCriteria implements IScreenCriteria {

	private ScreenType type;
	private int period1;
	private int range;
	private double alpha;
	private double bandPercent;
	private CrossDirection direction;
	private BandSelection band;

	public ExponentialMovingAverageBandCrossoverCriteria() {

	}

	public ExponentialMovingAverageBandCrossoverCriteria(int period1, int range, double alpha, double bandPercent,
			CrossDirection direction, BandSelection band) {
		this.type = ScreenType.EMA_PCT_BANDS;
		this.period1 = period1;
		this.range = range;
		this.alpha = alpha;
		this.bandPercent = bandPercent;
		this.direction = direction;
		this.band = band;
	}

	public ScreenType getType() {
		return type;
	}

	public int getPeriod1() {
		return period1;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public CrossDirection getDirection() {
		return direction;
	}

	public void setDirection(CrossDirection direction) {
		this.direction = direction;
	}

	public double getBandPercent() {
		return bandPercent;
	}

	public BandSelection getBand() {
		return band;
	}

}
