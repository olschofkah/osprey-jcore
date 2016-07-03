package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.criteria.ExponentialMovingAverageVsCurrentPriceCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class ExponentialMovingAverageVsCurrentPriceScreen implements IStockScreen {

	private final ExponentialMovingAverageVsCurrentPriceCriteria criteria;

	private boolean passed;

	public ExponentialMovingAverageVsCurrentPriceScreen(ExponentialMovingAverageVsCurrentPriceCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {
		
		if (criteria.getPeriod1() - 1 >= sqc.getHistoricalQuotes().size()) {
			throw new InsufficientHistoryException();
		}

		double ema1 = OspreyQuantMath.ema(sqc.getHistoricalQuotes().get(criteria.getPeriod1() - 1).getClose(),
				criteria.getPeriod1(), 0, sqc.getHistoricalQuotes());
		
		double close = sqc.getHistoricalQuotes().get(0).getClose();

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(ema1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(close).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(ema1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(close).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = ema1 > close;
			break;
		case _LE:
			passed = new BigDecimal(ema1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(close).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = ema1 < close;
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
