package com.osprey.screen.criteria;

public class ExponentialMovingAverageCrossoverCriteria implements IStockScreenCriteria {

	private StockScreenType type;
	private int period1;
	private int period2;
	private int range;
	private double alpha;
	private CrossDirection direction;

	public ExponentialMovingAverageCrossoverCriteria() {

	}

	public ExponentialMovingAverageCrossoverCriteria(int period1, int period2, int range, double alpha,
			CrossDirection direction) {
		this.type = StockScreenType.EMA_X;
		this.period1 = period1;
		this.period2 = period2;
		this.range = range;
		this.alpha = alpha;
		this.direction = direction;
	}

	public StockScreenType getType() {
		return type;
	}

	public int getPeriod1() {
		return period1;
	}

	public int getPeriod2() {
		return period2;
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

}
