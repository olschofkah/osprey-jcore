package com.osprey.screen.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.criteria.ExponentialMovingAverageCurrentPriceCrossoverCriteria;
import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class ExponentialMovingAverageCurrentPriceCrossoverScreen implements IStockScreen {
	final static Logger logger = LogManager.getLogger(ExponentialMovingAverageCurrentPriceCrossoverScreen.class);

	private final ExponentialMovingAverageCurrentPriceCrossoverCriteria criteria;
	private boolean passed;

	public ExponentialMovingAverageCurrentPriceCrossoverScreen(
			ExponentialMovingAverageCurrentPriceCrossoverCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double ema1;
		double close;
		int previousComp = 2;
		int comp;
		boolean isAboveToBelow = criteria.getDirection() == CrossDirection.FROM_ABOVE_TO_BELOW;

		for (int offset = criteria.getRange() - 1; offset >= 0; --offset) {

			ema1 = OspreyQuantMath.ema(criteria.getPeriod1(), offset, sqc.getHistoricalQuotes());
			close = sqc.getHistoricalQuotes().get(offset).getAdjClose();

			comp = close > ema1 ? 1 : (close < ema1 ? -1 : 0);

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
