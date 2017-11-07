package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.screen.criteria.MarketCapCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class MarketCapScreen implements IStockScreen {

	private final MarketCapCriteria criteria;
	private boolean passed;

	public MarketCapScreen(MarketCapCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double beta = sqc.getFundamentalQuote().getMarketCap();

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(beta).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getMarketCapComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(beta).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getMarketCapComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = beta > criteria.getMarketCapComparison();
			break;
		case _LE:
			passed = new BigDecimal(beta).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getMarketCapComparison()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = beta < criteria.getMarketCapComparison();
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
