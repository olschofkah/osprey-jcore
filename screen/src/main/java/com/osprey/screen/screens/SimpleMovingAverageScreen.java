package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.result.SMAPair;
import com.osprey.screen.criteria.SimpleMovingAverageCriteria;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;
import com.osprey.securitymaster.constants.OspreyConstants;

public class SimpleMovingAverageScreen implements IStockScreen {

	private final SimpleMovingAverageCriteria criteria;

	private boolean passed;

	public SimpleMovingAverageScreen(SimpleMovingAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(FundamentalPricedSecurity s, List<HistoricalSecurity> h) {

		SMAPair smaPair = OspreyQuantMath.smaPair(criteria.getPeriod1(), criteria.getPeriod2(), h);
		double sma1 = smaPair.getSma1();
		double sma2 = smaPair.getSma2();

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(sma1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(sma2).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(sma1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(sma2).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = sma1 > sma2;
			break;
		case _LE:
			passed = new BigDecimal(sma1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(sma2).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = sma1 < sma2;
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
