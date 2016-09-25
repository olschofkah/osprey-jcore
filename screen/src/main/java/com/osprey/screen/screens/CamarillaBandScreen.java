package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.CamarillaBandCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class CamarillaBandScreen implements IStockScreen {

	private final CamarillaBandCriteria criteria;

	private boolean passed;

	public CamarillaBandScreen(CamarillaBandCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double[] camarillaBands = OspreyQuantMath.camarillaBands(criteria.getPeriod(), 1, sqc.getHistoricalQuotes());

		// this is populated with the current quote for market hours ...
		// with 'close' being last.
		double quotePrice = sqc.getHistoricalQuotes().get(0).getClose();

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(quotePrice).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(camarillaBands[criteria.getBandIndex()])
							.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(quotePrice).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(camarillaBands[criteria.getBandIndex()])
							.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = quotePrice > camarillaBands[criteria.getBandIndex()];
			break;
		case _LE:
			passed = new BigDecimal(quotePrice).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(camarillaBands[criteria.getBandIndex()])
							.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = quotePrice < camarillaBands[criteria.getBandIndex()];
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
