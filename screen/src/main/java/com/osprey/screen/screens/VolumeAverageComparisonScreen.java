package com.osprey.screen.screens;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.VolumeAverageComparisonCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class VolumeAverageComparisonScreen extends NumericalRelationalComparisonStockScreen {

	private final VolumeAverageComparisonCriteria criteria;

	public VolumeAverageComparisonScreen(VolumeAverageComparisonCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double volumeAverage1 = OspreyQuantMath.volumeAverage(sqc.getHistoricalQuotes(), criteria.getPeriod1(), 0);
		double volumeAverage2 = OspreyQuantMath.volumeAverage(sqc.getHistoricalQuotes(), criteria.getPeriod2(), 0);

		compare(volumeAverage1, volumeAverage2);

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
