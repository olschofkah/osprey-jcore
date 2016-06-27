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
		int previousComp = 2;
		int comp;
		boolean isAboveToBelow = criteria.getDirection() == CrossDirection.FROM_ABOVE_TO_BELOW;
		for (int offset = criteria.getRange(); offset >= 0; --offset) {

			SMAPair smaPair = OspreyQuantMath.smaPair(criteria.getPeriod1() + offset, criteria.getPeriod2() + offset,
					sqc.getHistoricalQuotes());

			ema1 = OspreyQuantMath.emaSmooth(smaPair.getSma1(), smaPair.getPeriod1(), criteria.getAlpha(), offset,
					sqc.getHistoricalQuotes());
			ema2 = OspreyQuantMath.emaSmooth(smaPair.getSma2(), smaPair.getPeriod2(), criteria.getAlpha(), offset,
					sqc.getHistoricalQuotes());

			comp = ema1 > ema2 ? 1 : (ema1 < ema2 ? -1 : 0);

			if (previousComp == 2) {
				previousComp = comp;
				continue;
			}

			if (((isAboveToBelow && previousComp == 1) || (!isAboveToBelow && previousComp == -1))
					&& previousComp != comp) {
				passed = true;
				break;
			}

			previousComp = comp;
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
