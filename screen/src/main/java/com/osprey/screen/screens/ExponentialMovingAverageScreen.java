package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.criteria.ExponentialMovingAverageCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class ExponentialMovingAverageScreen implements IStockScreen {

	private final ExponentialMovingAverageCriteria criteria;

	private boolean passed;

	public ExponentialMovingAverageScreen(ExponentialMovingAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		if (criteria.getPeriod1() - 1 >= sqc.getHistoricalQuotes().size()
				|| criteria.getPeriod2() - 1 >= sqc.getHistoricalQuotes().size()) {
			throw new InsufficientHistoryException();
		}

		double ema1 = OspreyQuantMath.ema(sqc.getHistoricalQuotes().get(criteria.getPeriod1() - 1).getClose(),
				criteria.getPeriod1(), 0, sqc.getHistoricalQuotes());
		double ema2 = OspreyQuantMath.ema(sqc.getHistoricalQuotes().get(criteria.getPeriod2() - 1).getClose(),
				criteria.getPeriod2(), 0, sqc.getHistoricalQuotes());

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(ema1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(ema2).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(ema1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(ema2).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = ema1 > ema2;
			break;
		case _LE:
			passed = new BigDecimal(ema1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(ema2).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = ema1 < ema2;
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
