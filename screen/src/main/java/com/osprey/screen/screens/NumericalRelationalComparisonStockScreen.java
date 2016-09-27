package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.constants.OspreyConstants;

public abstract class NumericalRelationalComparisonStockScreen implements IStockScreen {

	protected boolean passed;

	protected abstract RelationalOperator getRelationalOperator();

	/**
	 * c1.compareTo(c2) with control over precision on the equal comparisons and
	 * >= and <= taken into account. The {@code getRelationalOperator()} will be
	 * called to determine what a successful comparison is. The member attribute
	 * {@code passed} is set to the result as well as returned for convenience.
	 * 
	 * @param c1
	 * @param c2
	 * @param operator
	 * @return
	 */
	protected boolean compare(double c1, double c2) {
		switch (getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(c1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(c2).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(c1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(c2).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = c1 > c2;
			break;
		case _LE:
			passed = new BigDecimal(c1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(c2).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = c1 < c2;
			break;
		default:
			passed = false;
			break;

		}

		return passed;
	}

	public boolean passed() {
		return passed;
	}

}
