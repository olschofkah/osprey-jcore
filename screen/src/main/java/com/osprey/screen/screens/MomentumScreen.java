package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.MomentumCriteria;
import com.osprey.screen.criteria.VolatilityCriteria;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class MomentumScreen implements IStockScreen {

	private final MomentumCriteria criteria;

	private boolean passed;

	public MomentumScreen(MomentumCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		
//		double closingPrice = sqc.getSecurity().getPreviousClose(); 
//		List<HistoricalQuote> closePrice_20day = sqc.getHistoricalQuotes();
//		
//		OspreyQuantMath.
//		
//		
//		switch (criteria.getOperator()) {
//		case _EQ:
//			passed = new BigDecimal(volatility).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
//					.compareTo(new BigDecimal(criteria.getVolatilityComparison()).setScale(OspreyConstants.PRICE_SCALE,
//							RoundingMode.HALF_UP)) == 0;
//			break;
//		case _GE:
//			passed = new BigDecimal(volatility).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
//					.compareTo(new BigDecimal(criteria.getVolatilityComparison()).setScale(OspreyConstants.PRICE_SCALE,
//							RoundingMode.HALF_UP)) >= 0;
//			break;
//		case _GT:
//			passed = volatility > criteria.getVolatilityComparison();
//			break;
//		case _LE:
//			passed = new BigDecimal(volatility).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
//					.compareTo(new BigDecimal(criteria.getVolatilityComparison()).setScale(OspreyConstants.PRICE_SCALE,
//							RoundingMode.HALF_UP)) <= 0;
//			break;
//		case _LT:
//			passed = volatility < criteria.getVolatilityComparison();
//			break;
//		default:
//			passed = false;
//			break;
//
//		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
