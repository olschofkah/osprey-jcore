package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.VolumeAverageComparisonCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class VolumeAverageComparisonScreen implements IStockScreen {

	private final VolumeAverageComparisonCriteria criteria;
	private boolean passed;

	public VolumeAverageComparisonScreen(VolumeAverageComparisonCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double volumeAverage1 = OspreyQuantMath.volumeAverage(sqc.getHistoricalQuotes(), criteria.getPeriod1(), 0);
		double volumeAverage2 = OspreyQuantMath.volumeAverage(sqc.getHistoricalQuotes(), criteria.getPeriod2(), 0);

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(volumeAverage1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(volumeAverage2).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(volumeAverage1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(volumeAverage2).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = volumeAverage1 > volumeAverage2;
			break;
		case _LE:
			passed = new BigDecimal(volumeAverage1).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(volumeAverage2).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = volumeAverage1 < volumeAverage2;
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
