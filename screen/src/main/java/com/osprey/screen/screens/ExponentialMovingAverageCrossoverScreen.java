package com.osprey.screen.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.ExponentialMovingAverageCrossoverCriteria;
import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class ExponentialMovingAverageCrossoverScreen implements IStockScreen {
	final static Logger logger = LogManager.getLogger(ExponentialMovingAverageCrossoverScreen.class);

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

		// TODO Consider refactoring to return an array of emas instead of recalculating it for performance. 
		
		for (int offset = criteria.getRange() - 1; offset >= 0; --offset) {

			ema1 = OspreyQuantMath.ema(criteria.getPeriod1(), offset, sqc.getHistoricalQuotes());
			ema2 = OspreyQuantMath.ema(criteria.getPeriod2(), offset, sqc.getHistoricalQuotes());

			// System.out.println(ema1 + " " + ema2);

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
