package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.screen.criteria.PreviousClosePriceCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class PreviousClosePriceScreen implements IStockScreen {

	private final PreviousClosePriceCriteria criteria;
	private boolean passed;

	public PreviousClosePriceScreen(PreviousClosePriceCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double closingPrice = sqc.getSecurity().getPreviousClose();
		double price = criteria.getPrice();

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(closingPrice).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(price).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(closingPrice).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(price).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = closingPrice > price;
			break;
		case _LE:
			passed = new BigDecimal(closingPrice).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(price).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = closingPrice < price;
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
