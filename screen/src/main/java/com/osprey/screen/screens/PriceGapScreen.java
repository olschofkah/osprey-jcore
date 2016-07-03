package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.criteria.PriceGapCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class PriceGapScreen implements IStockScreen {

	private final PriceGapCriteria criteria;
	private boolean passed;

	public PriceGapScreen(PriceGapCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		if (sqc.getHistoricalQuotes().size() < 2) {
			throw new InsufficientHistoryException();
		}

		double p0 = sqc.getHistoricalQuotes().get(0).getOpen();
		double p1 = sqc.getHistoricalQuotes().get(1).getClose();

		double pctGap = 1 - (p0 / p1);

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(pctGap).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getPercentGap()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(pctGap).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getPercentGap()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = pctGap > criteria.getPercentGap();
			break;
		case _LE:
			passed = new BigDecimal(pctGap).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getPercentGap()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = pctGap < criteria.getPercentGap();
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
