package com.osprey.screen.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InvalidPeriodException;
import com.osprey.math.result.StochasticOscillatorCurve;
import com.osprey.screen.criteria.StochasticFullOscillatorLevelCrossCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class StochasticFullOscillatorLevelCrossScreen implements IStockScreen {
	final static Logger logger = LogManager.getLogger(StochasticFullOscillatorLevelCrossScreen.class);

	private final StochasticFullOscillatorLevelCrossCriteria criteria;

	private boolean passed;

	public StochasticFullOscillatorLevelCrossScreen(StochasticFullOscillatorLevelCrossCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		StochasticOscillatorCurve curves = OspreyQuantMath.stochasticOscillatorSmaCurves(criteria.getPeriodLookBack(),
				criteria.getPeriodK(), criteria.getPeriodD(), criteria.getRange(), sqc.getHistoricalQuotes());

		try {
			passed = curves.checkLevelCross(0, criteria.getRange(), criteria.getLevel(),
					criteria.getDirection().getCode());
		} catch (InvalidPeriodException e) {
			passed = false;
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
