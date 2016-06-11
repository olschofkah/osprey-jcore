package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.ExponentialMovingAverageCriteria;
import com.osprey.securitymaster.ExtendedPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;
import com.osprey.securitymaster.constants.ISMConstants;

public class ExponentialMovingAverageScreen implements IStockScreen {

	private final ExponentialMovingAverageCriteria criteria;

	private boolean passed;

	public ExponentialMovingAverageScreen(ExponentialMovingAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(ExtendedPricedSecurity s, List<HistoricalSecurity> h) {

		int d1 = criteria.getD1();
		int d2 = criteria.getD2();
		int r = d1 > d2 ? d1 : d2;

		double sma1 = 0;
		double sma2 = 0;

		double histPrice = 0;
		for (int i = 0; i < r; ++i) {
			histPrice = h.get(i).getClosingPrice();
			if (i <= d1) {
				sma1 += histPrice;
			}
			if (i <= d2) {
				sma2 += histPrice;
			}
		}

		sma1 /= d1;
		sma2 /= d2;

		double ema1 = OspreyQuantMath.ema(sma1, d1, h);
		double ema2 = OspreyQuantMath.ema(sma2, d2, h);

		switch (criteria.getOperator()) {
		case _EQ:
			passed = new BigDecimal(ema1).setScale(ISMConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(ema2).setScale(ISMConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(ema1).setScale(ISMConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(ema2).setScale(ISMConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = ema1 > ema2;
			break;
		case _LE:
			passed = new BigDecimal(ema1).setScale(ISMConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(ema2).setScale(ISMConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
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
