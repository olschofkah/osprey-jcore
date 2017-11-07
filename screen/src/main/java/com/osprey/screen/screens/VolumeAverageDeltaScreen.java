package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.VolumeAverageDeltaCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class VolumeAverageDeltaScreen implements IStockScreen {

	private final VolumeAverageDeltaCriteria criteria;
	private boolean passed;

	public VolumeAverageDeltaScreen(VolumeAverageDeltaCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double historicalVolume;
		double volumeDeltaPct;
		double currentVolume = sqc.getSecurityQuote().getVolume();

		for (int i = 0; i < criteria.getRange(); ++i) {

			historicalVolume = OspreyQuantMath.volumeAverage(sqc.getHistoricalQuotes(), criteria.getPeriod(),
					i * criteria.getPeriod());

			volumeDeltaPct = 1 - (historicalVolume / currentVolume);

			switch (criteria.getRelationalOperator()) {
			case _EQ:
				passed = new BigDecimal(volumeDeltaPct).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getDeltaPercent()).setScale(OspreyConstants.PRICE_SCALE,
								RoundingMode.HALF_UP)) == 0;
				break;
			case _GE:
				passed = new BigDecimal(volumeDeltaPct).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getDeltaPercent()).setScale(OspreyConstants.PRICE_SCALE,
								RoundingMode.HALF_UP)) >= 0;
				break;
			case _GT:
				passed = volumeDeltaPct > criteria.getDeltaPercent();
				break;
			case _LE:
				passed = new BigDecimal(volumeDeltaPct).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getDeltaPercent()).setScale(OspreyConstants.PRICE_SCALE,
								RoundingMode.HALF_UP)) <= 0;
				break;
			case _LT:
				passed = volumeDeltaPct < criteria.getDeltaPercent();
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
