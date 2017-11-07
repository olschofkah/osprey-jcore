package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.criteria.PricePercentageChangeCriteria;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class PricePercentageChangeScreen implements IStockScreen {

	private final PricePercentageChangeCriteria criteria;
	private boolean passed;

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

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(pctGap).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getPercentChange()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(pctGap).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getPercentChange()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = pctGap > criteria.getPercentChange();
			break;
		case _LE:
			passed = new BigDecimal(pctGap).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getPercentChange()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = pctGap < criteria.getPercentChange();
			break;
		default:
			passed = false;
			break;

		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
