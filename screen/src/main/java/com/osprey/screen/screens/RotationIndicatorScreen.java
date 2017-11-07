package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.RotationIndicatorCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class RotationIndicatorScreen implements IStockScreen {

	private final RotationIndicatorCriteria criteria;

	private boolean passed;

	public RotationIndicatorScreen(RotationIndicatorCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double ri = OspreyQuantMath.rotationIndicator(criteria.getLongPeriod(), criteria.getShortPeriod(), 0,
				criteria.getLag(), sqc.getHistoricalQuotes());

		switch (criteria.getRelationalOperator()) {
		case _EQ:
			passed = new BigDecimal(ri).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getRotationBenchmark()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passed = new BigDecimal(ri).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getRotationBenchmark()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passed = ri > criteria.getRotationBenchmark();
			break;
		case _LE:
			passed = new BigDecimal(ri).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
					.compareTo(new BigDecimal(criteria.getRotationBenchmark()).setScale(OspreyConstants.PRICE_SCALE,
							RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passed = ri < criteria.getRotationBenchmark();
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
