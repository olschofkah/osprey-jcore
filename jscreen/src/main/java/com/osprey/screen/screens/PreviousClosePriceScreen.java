package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.osprey.screen.criteria.PreviousClosePriceCriteria;
import com.osprey.screen.criteria.SimpleMovingAverageCriteria;
import com.osprey.securitymaster.ExtendedPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;
import com.osprey.securitymaster.constants.ISMConstants;

public class PreviousClosePriceScreen implements IStockScreen {

	private final PreviousClosePriceCriteria criteria;
	private boolean passed;

	public PreviousClosePriceScreen(PreviousClosePriceCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(ExtendedPricedSecurity s, List<HistoricalSecurity> h) {

		double closingPrice = s.getClosingPrice();
		double price = criteria.getPrice();

		switch (criteria.getOperator()) {
		case _EQ:
			passed = new BigDecimal(closingPrice).setScale(ISMConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(price).setScale(ISMConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(closingPrice).setScale(ISMConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(price).setScale(ISMConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = closingPrice > price;
			break;
		case _LE:
			passed = new BigDecimal(closingPrice).setScale(ISMConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(price).setScale(ISMConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
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
