package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.RelativeStrengthIndexCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class RelativeStrengthIndexScreen implements IStockScreen {

	private final RelativeStrengthIndexCriteria criteria;

	private boolean passed;

	public RelativeStrengthIndexScreen(RelativeStrengthIndexCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {
		
		double rsi;
		for (int i = 0; i < criteria.getPeriodRange(); ++i) {

			rsi = OspreyQuantMath.rsiUsingWilders(criteria.getPeriod(), i, sqc.getHistoricalQuotes());

			switch (criteria.getRelationalOperator()) {
			case _EQ:
				passed = new BigDecimal(rsi).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getRsiComparison()).setScale(OspreyConstants.PRICE_SCALE,
								RoundingMode.HALF_UP)) == 0;
				break;
			case _GE:
				passed = new BigDecimal(rsi).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getRsiComparison()).setScale(OspreyConstants.PRICE_SCALE,
								RoundingMode.HALF_UP)) >= 0;
				break;
			case _GT:
				passed = rsi > criteria.getRsiComparison();
				break;
			case _LE:
				passed = new BigDecimal(rsi).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getRsiComparison()).setScale(OspreyConstants.PRICE_SCALE,
								RoundingMode.HALF_UP)) <= 0;
				break;
			case _LT:
				passed = rsi < criteria.getRsiComparison();
				break;
			default:
				passed = false;
				break;

			}

			if (passed) {
				break;
			}
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
