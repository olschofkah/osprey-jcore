package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.screen.criteria.BetaCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class BetaScreen implements IStockScreen {

	private final BetaCriteria criteria;
	private boolean passed;

	public BetaScreen(BetaCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double beta = sqc.getFundamentalQuote().getBeta();

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(beta).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getBetaComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(beta).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getBetaComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = beta > criteria.getBetaComparison();
			break;
		case _LE:
			passed = new BigDecimal(beta).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getBetaComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = beta < criteria.getBetaComparison();
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
