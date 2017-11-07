package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.screen.criteria.constants.ScreenType;

public class MomentumCriteria implements IScreenCriteria {

	private ScreenType type;
	private int period1;
	private int range;
	private double crossoverPoint;
	private CrossDirection direction;

	public MomentumCriteria() {

	}

	public MomentumCriteria(int period1, int range, double crossoverPoint, CrossDirection direction) {
		this.type = ScreenType.MOMENTUM_X;
		this.period1 = period1;
		this.range = range;
		this.direction = direction;
		this.crossoverPoint = crossoverPoint;
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

	public double getCrossoverPoint(){
		return crossoverPoint;
	}
	
	public CrossDirection getDirection() {
		return direction;
	}

}
