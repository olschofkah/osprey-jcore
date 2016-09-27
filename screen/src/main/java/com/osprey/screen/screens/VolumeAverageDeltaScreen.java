package com.osprey.screen.screens;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.VolumeAverageDeltaCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class VolumeAverageDeltaScreen extends NumericalRelationalComparisonStockScreen {

	private final VolumeAverageDeltaCriteria criteria;

	public VolumeAverageDeltaScreen(VolumeAverageDeltaCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double historicalVolume;
		double volumeDeltaPct;
		double currentVolume = sqc.getSecurityQuote().getVolume();

		for (int i = 0; i < criteria.getRange(); ++i) {

			historicalVolume = OspreyQuantMath.volumeAverage(sqc.getHistoricalQuotes(), criteria.getPeriod(),
					i * criteria.getPeriod());

			volumeDeltaPct = 1 - (historicalVolume / currentVolume);

			compare(volumeDeltaPct, criteria.getDeltaPercent());

			if (passed) {
				break;
			}
		}

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
