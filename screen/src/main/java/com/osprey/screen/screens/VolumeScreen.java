package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.screen.criteria.VolumeCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class VolumeScreen implements IStockScreen {

	private final VolumeCriteria criteria;

	private boolean passed;

	public VolumeScreen(VolumeCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double volume;
		for (int offset = criteria.getRange() - 1; offset >= 0; --offset) {
			
			volume = sqc.getHistoricalQuotes().get(offset).getVolume();

			switch (criteria.getRelationalOperator()) {
			case _EQ:
				passed = new BigDecimal(volume).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getVolumeComparison())
								.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
				break;
			case _GE:
				passed = new BigDecimal(volume).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getVolumeComparison())
								.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
				break;
			case _GT:
				passed = volume > criteria.getVolumeComparison();
				break;
			case _LE:
				passed = new BigDecimal(volume).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getVolumeComparison())
								.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
				break;
			case _LT:
				passed = volume < criteria.getVolumeComparison();
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
