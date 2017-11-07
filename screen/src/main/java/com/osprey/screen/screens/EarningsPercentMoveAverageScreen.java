package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.math.EarningsCalculator;
import com.osprey.screen.criteria.EarningsPercentMoveAverageCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class EarningsPercentMoveAverageScreen implements IStockScreen {

	private final EarningsPercentMoveAverageCriteria criteria;

	private boolean passed;

	public EarningsPercentMoveAverageScreen(EarningsPercentMoveAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double moveAverage = EarningsCalculator.calcEarningsPercentMove(sqc, criteria.getDaysBefore(),
				criteria.getDaysAfter());

		if (moveAverage <= 0) {
			passed = false;
			return this;
		}

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(moveAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getPercentMoveComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(moveAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getPercentMoveComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = moveAverage > criteria.getPercentMoveComparison();
			break;
		case _LE:
			passed = new BigDecimal(moveAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getPercentMoveComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = moveAverage < criteria.getPercentMoveComparison();
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
