package com.osprey.screen.criteria;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.screen.criteria.constants.ScreenType;

public class BetaCriteria implements IScreenCriteria {

	private ScreenType type;
	private double betaComparison;
	private RelationalOperator relationalOperator;

	public BetaCriteria() {

	}

	public BetaCriteria(double betaComparison, RelationalOperator relationalOperator) {
		type = ScreenType.BETA;
		this.relationalOperator = relationalOperator;
		this.betaComparison = betaComparison;
	}

	public ScreenType getType() {
		return type;
	}

	public double getBetaComparison() {
		return betaComparison;
	}

	public RelationalOperator getRelationalOperator() {
		return relationalOperator;
	}

}
