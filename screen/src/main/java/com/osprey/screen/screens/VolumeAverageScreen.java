package com.osprey.screen.screens;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.VolumeAverageCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class VolumeAverageScreen extends NumericalRelationalComparisonStockScreen {

	private final VolumeAverageCriteria criteria;

	public VolumeAverageScreen(VolumeAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double volumeAverage = OspreyQuantMath.volumeAverage(sqc.getHistoricalQuotes(), criteria.getPeriod(), 0);

		compare(volumeAverage, criteria.getBenchmarkVolume());

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
