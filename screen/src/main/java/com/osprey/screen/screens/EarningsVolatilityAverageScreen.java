package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.math.EarningsCalculator;
import com.osprey.screen.criteria.EarningsVolatilityAverageCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class EarningsVolatilityAverageScreen implements IStockScreen {

	private final EarningsVolatilityAverageCriteria criteria;

	private boolean passed;

	public EarningsVolatilityAverageScreen(EarningsVolatilityAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double volatilityAverage = EarningsCalculator.calcEarningsVolatility(sqc, criteria.getDaysBefore(),
				criteria.getDaysAfter());

		if (volatilityAverage <= 0) {
			passed = false;
			return this;
		}

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(volatilityAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getVolatilityComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(volatilityAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getVolatilityComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = volatilityAverage > criteria.getVolatilityComparison();
			break;
		case _LE:
			passed = new BigDecimal(volatilityAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getVolatilityComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = volatilityAverage < criteria.getVolatilityComparison();
			break;
		default:
			passed = false;
			break;

		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
