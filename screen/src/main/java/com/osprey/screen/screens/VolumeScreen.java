package com.osprey.screen.screens;

import com.osprey.screen.criteria.VolumeCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class VolumeScreen extends NumericalRelationalComparisonStockScreen {

	private final VolumeCriteria criteria;

	public VolumeScreen(VolumeCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double volume;
		for (int offset = criteria.getRange() - 1; offset >= 0; --offset) {

			volume = sqc.getHistoricalQuotes().get(offset).getVolume();

			compare(volume, criteria.getVolumeComparison());

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
