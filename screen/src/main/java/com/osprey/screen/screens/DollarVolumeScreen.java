package com.osprey.screen.screens;

import java.util.List;

import com.osprey.screen.criteria.DollarVolumeCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class DollarVolumeScreen extends NumericalRelationalComparisonStockScreen {

	private final DollarVolumeCriteria criteria;

	public DollarVolumeScreen(DollarVolumeCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double dollarVolume;
		HistoricalQuote historicalQuote;

		List<HistoricalQuote> historicalQuotes = sqc.getHistoricalQuotes();

		// Override the range if we do not have the history for it.
		int range = criteria.getRange();
		if (historicalQuotes.size() < range) {
			range = historicalQuotes.size();
		}

		for (int offset = range - 1; offset >= 0; --offset) {
			historicalQuote = historicalQuotes.get(offset);
			dollarVolume = historicalQuote.getVolume() * historicalQuote.getAdjClose();

			compare(dollarVolume, criteria.getDollarVolumeComparison());

		}

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}

}
