package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.VolumeAverageCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class VolumeAverageScreen implements IStockScreen {

	private final VolumeAverageCriteria criteria;
	private boolean passed;

	public VolumeAverageScreen(VolumeAverageCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double volumeAverage = OspreyQuantMath.volumeAverage(sqc.getHistoricalQuotes(), criteria.getPeriod(), 0);

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(volumeAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getBenchmarkVolume()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(volumeAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getBenchmarkVolume()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = volumeAverage > criteria.getBenchmarkVolume();
			break;
		case _LE:
			passed = new BigDecimal(volumeAverage).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getBenchmarkVolume()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = volumeAverage < criteria.getBenchmarkVolume();
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
