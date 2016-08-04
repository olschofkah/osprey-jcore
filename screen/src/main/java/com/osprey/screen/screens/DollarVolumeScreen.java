package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.screen.criteria.DollarVolumeCriteria;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class DollarVolumeScreen implements IStockScreen {

	private final DollarVolumeCriteria criteria;

	private boolean passed;

	public DollarVolumeScreen(DollarVolumeCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double dollarVolume;
		HistoricalQuote historicalQuote;
		for (int offset = criteria.getRange() - 1; offset >= 0; --offset) {
			historicalQuote = sqc.getHistoricalQuotes().get(offset);
			dollarVolume = historicalQuote.getVolume() * historicalQuote.getAdjClose();

			switch (criteria.getRelationalOperator()) {
			case _EQ:
				passed = new BigDecimal(dollarVolume).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getDollarVolumeComparison())
								.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
				break;
			case _GE:
				passed = new BigDecimal(dollarVolume).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getDollarVolumeComparison())
								.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
				break;
			case _GT:
				passed = dollarVolume > criteria.getDollarVolumeComparison();
				break;
			case _LE:
				passed = new BigDecimal(dollarVolume).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getDollarVolumeComparison())
								.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
				break;
			case _LT:
				passed = dollarVolume < criteria.getDollarVolumeComparison();
				break;
			default:
				passed = false;
				break;

			}
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
