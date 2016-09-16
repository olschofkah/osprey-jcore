package com.osprey.screen.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.MoneyFlowIndexLevelCrossCriteria;
import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class MoneyFlowIndexLevelCrossScreen implements IStockScreen {
	final static Logger logger = LogManager.getLogger(MoneyFlowIndexLevelCrossScreen.class);

	private final MoneyFlowIndexLevelCrossCriteria criteria;

	private boolean passed;

	public MoneyFlowIndexLevelCrossScreen(MoneyFlowIndexLevelCrossCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double mfi;
		double level = criteria.getLevel();
		int previousComp = 2;
		int comp;
		boolean isAboveToBelow = criteria.getDirection() == CrossDirection.FROM_ABOVE_TO_BELOW;
		
		for (int offset = criteria.getRange() - 1; offset >= 0; --offset) {

			mfi = OspreyQuantMath.moneyFlowIndex(criteria.getPeriod(), offset, sqc.getHistoricalQuotes());
			comp = mfi > level ? 1 : (mfi < level ? -1 : 0);

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
