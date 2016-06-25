package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.VolatilityCriteria;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.constants.OspreyConstants;

public class VolatilityScreen implements IStockScreen {

	private final VolatilityCriteria criteria;

	private boolean passed;

	public VolatilityScreen(VolatilityCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(FundamentalPricedSecurity s, List<HistoricalQuote> h) {

		double volatility = OspreyQuantMath.volatility(criteria.getPeriod(), h);

		switch (criteria.getOperator()) {
		case _EQ:
			passed = new BigDecimal(volatility).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getVolatilityComparison()).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(volatility).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getVolatilityComparison()).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = volatility > criteria.getVolatilityComparison();
			break;
		case _LE:
			passed = new BigDecimal(volatility).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getVolatilityComparison()).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = volatility < criteria.getVolatilityComparison();
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
