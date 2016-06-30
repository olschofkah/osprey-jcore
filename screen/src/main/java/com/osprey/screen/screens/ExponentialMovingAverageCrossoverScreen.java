package com.osprey.screen.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.criteria.CrossDirection;
import com.osprey.screen.criteria.ExponentialMovingAverageCrossoverCriteria;
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
		double sma1;
		double sma2;
		int previousComp = 2;
		int comp;
		boolean isAboveToBelow = criteria.getDirection() == CrossDirection.FROM_ABOVE_TO_BELOW;
		int hisotrySize = sqc.getHistoricalQuotes().size();

		double alpha1;
		double alpha2;
		if (criteria.getAlpha() == 0.0) {
			alpha1 = 2.0 / (criteria.getPeriod1() + 1.0);
			alpha2 = 2.0 / (criteria.getPeriod2() + 1.0);
		} else {
			alpha1 = criteria.getAlpha();
			alpha2 = criteria.getAlpha();
		}
		
		try {
			
			if (criteria.getPeriod1() + criteria.getRange() - 1 > hisotrySize
					|| criteria.getPeriod2() + criteria.getRange() - 1 > hisotrySize) {
				throw new InsufficientHistoryException();
			}

			for (int offset = criteria.getRange() - 1; offset >= 0; --offset) {

				// TODO verify to either use sma or sqc.getHistoricalQuotes().get(criteria.getPeriod() + offset).getAdjClose() as seed to ema

//				sma1 = OspreyQuantMath.sma(10, criteria.getPeriod1() + offset - 1, sqc.getHistoricalQuotes(),
//						sqc.getSecurityQuote());
//				sma2 = OspreyQuantMath.sma(10, criteria.getPeriod2() + offset - 1, sqc.getHistoricalQuotes(),
//						sqc.getSecurityQuote());

				ema1 = OspreyQuantMath.emaSmooth(
						sqc.getHistoricalQuotes().get(criteria.getPeriod1() + offset).getAdjClose(),
						criteria.getPeriod1(), alpha1, offset, sqc.getHistoricalQuotes(), sqc.getSecurityQuote());
				ema2 = OspreyQuantMath.emaSmooth(
						sqc.getHistoricalQuotes().get(criteria.getPeriod2() + offset).getAdjClose(),
						criteria.getPeriod2(), alpha2, offset, sqc.getHistoricalQuotes(), sqc.getSecurityQuote());

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
			
		} catch (InsufficientHistoryException e) {
			passed = false;
			logger.error("Insufficient historical prices to calculate ema/sma on {}, size avaialble {} ",
					new Object[] { sqc.getKey().getSymbol(), sqc.getHistoricalQuotes().size() });
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
