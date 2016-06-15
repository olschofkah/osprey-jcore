package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.result.SMAPair;
import com.osprey.screen.criteria.ExponentialMovingAverageCriteria;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;
import com.osprey.securitymaster.constants.OspreyConstants;

public class ExponentialMovingAverageScreen implements IStockScreen {

	private final ExponentialMovingAverageCriteria criteria;

	private boolean passed;

	public ExponentialMovingAverageScreen(ExponentialMovingAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(FundamentalPricedSecurity s, List<HistoricalSecurity> h) {

		SMAPair smaPair = OspreyQuantMath.smaPair(criteria.getPeriod1(), criteria.getPeriod2(), h);
		double ema1 = OspreyQuantMath.ema(smaPair.getSma1(), smaPair.getPeriod1(), h);
		double ema2 = OspreyQuantMath.ema(smaPair.getSma2(), smaPair.getPeriod2(), h);

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(ema1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(ema2).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(ema1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(ema2).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = ema1 > ema2;
			break;
		case _LE:
			passed = new BigDecimal(ema1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(ema2).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = ema1 < ema2;
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
