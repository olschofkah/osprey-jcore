package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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
		
		List<HistoricalQuote> historicalQuotes = sqc.getHistoricalQuotes();
		
		// Override the range if we do not have the history for it. 
		int range = criteria.getRange();
		if(historicalQuotes.size() < range){
			range = historicalQuotes.size();
		}
		
		for (int offset = range - 1; offset >= 0 ; --offset) {
			historicalQuote = historicalQuotes.get(offset);
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
