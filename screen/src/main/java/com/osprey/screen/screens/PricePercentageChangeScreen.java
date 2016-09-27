package com.osprey.screen.screens;

import java.util.List;

import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.criteria.PricePercentageChangeCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class PricePercentageChangeScreen extends NumericalRelationalComparisonStockScreen {

	private final PricePercentageChangeCriteria criteria;

	public PricePercentageChangeScreen(PricePercentageChangeCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		if (sqc.getHistoricalQuotes().size() < criteria.getPeriod() - 1) {
			throw new InsufficientHistoryException();
		}

		List<HistoricalQuote> historicalQuotes = sqc.getHistoricalQuotes();
		if (historicalQuotes.size() < criteria.getPeriod()) {
			passed = false;
			return this;
		}

		double close0 = historicalQuotes.get(0).getAdjClose();
		double close1 = historicalQuotes.get(criteria.getPeriod() - 1).getAdjClose();

		double pctGap = 1 - (close0 / close1);

		compare(pctGap, criteria.getPercentChange());

		return this;
	}

	public boolean passed() {
		return passed;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}

}
