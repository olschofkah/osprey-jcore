package com.osprey.screen.screens;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.result.SMAPair;
import com.osprey.screen.criteria.CrossDirection;
import com.osprey.screen.criteria.ExponentialMovingAverageCrossoverCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class ExponentialMovingAverageCrossoverScreen implements IStockScreen {

	private final ExponentialMovingAverageCrossoverCriteria criteria;

	private boolean passed;

	public ExponentialMovingAverageCrossoverScreen(ExponentialMovingAverageCrossoverCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double ema1;
		double ema2;
		double sma1;
		double sma2;
		int previousComp = 2;
		int comp;
		boolean isAboveToBelow = criteria.getDirection() == CrossDirection.FROM_ABOVE_TO_BELOW;

		double alpha1;
		double alpha2;
		if (criteria.getAlpha() == 0.0) {
			alpha1 = 2.0 / (criteria.getPeriod1() + 1.0);
			alpha2 = 2.0 / (criteria.getPeriod2() + 1.0);
		} else {
			alpha1 = criteria.getAlpha();
			alpha2 = criteria.getAlpha();
		}

		for (int offset = criteria.getRange() - 1; offset >= 0; --offset) {

			// TODO verify 
			
			sma1 = OspreyQuantMath.sma(10, criteria.getPeriod1() + offset - 1, sqc.getHistoricalQuotes(),
					sqc.getSecurityQuote());
			sma2 = OspreyQuantMath.sma(10, criteria.getPeriod2() + offset - 1, sqc.getHistoricalQuotes(),
					sqc.getSecurityQuote());

			ema1 = OspreyQuantMath.emaSmooth(sma1, criteria.getPeriod1(), alpha1, offset, sqc.getHistoricalQuotes(),
					sqc.getSecurityQuote());
			ema2 = OspreyQuantMath.emaSmooth(sma2, criteria.getPeriod2(), alpha2, offset,
					sqc.getHistoricalQuotes(), sqc.getSecurityQuote());

			comp = ema1 > ema2 ? 1 : (ema1 < ema2 ? -1 : 0);

			if (previousComp == 2) {
				previousComp = comp;
				continue;
			}

			if (((isAboveToBelow && previousComp == 1) || (!isAboveToBelow && previousComp == -1))
					&& previousComp != comp) {
				passed = true;
			//	break;
			}

			previousComp = comp;
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
