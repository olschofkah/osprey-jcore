package com.osprey.screen.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.ExponentialMovingAverageBandCrossoverCriteria;
import com.osprey.screen.criteria.constants.BandSelection;
import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class ExponentialMovingAverageBandCrossoverScreen implements IStockScreen {
	final static Logger logger = LogManager.getLogger(ExponentialMovingAverageBandCrossoverScreen.class);

	private final ExponentialMovingAverageBandCrossoverCriteria criteria;

	private boolean passed;

	public ExponentialMovingAverageBandCrossoverScreen(ExponentialMovingAverageBandCrossoverCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double ema1;
		double upperBand;
		double lowerBand;
		double price;

		int previousComp = 2;
		int comp;
		boolean isAboveToBelow = criteria.getDirection() == CrossDirection.FROM_ABOVE_TO_BELOW;

		for (int offset = criteria.getRange() - 1; offset >= 0; --offset) {

			ema1 = OspreyQuantMath.ema(criteria.getPeriod1(), offset, sqc.getHistoricalQuotes());

			price = sqc.getHistoricalQuotes().get(offset).getAdjClose();

			upperBand = ema1 * (1 + criteria.getBandPercent());
			lowerBand = ema1 * (1 - criteria.getBandPercent());

			if (criteria.getBand() == BandSelection.UPPER_BAND) {
				comp = price > upperBand ? 1 : (price < upperBand ? -1 : 0);
			} else { // lower band
				comp = price > lowerBand ? 1 : (price < lowerBand ? -1 : 0);
			}

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
