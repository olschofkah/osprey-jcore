package com.osprey.screen.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.result.StochasticOscillatorCurve;
import com.osprey.screen.criteria.StochasticFullOscillatorSignalCrossCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class StochasticFullOscillatorSignalCrossScreen implements IStockScreen {
	final static Logger logger = LogManager.getLogger(StochasticFullOscillatorSignalCrossScreen.class);

	private final StochasticFullOscillatorSignalCrossCriteria criteria;

	private boolean passed;

	public StochasticFullOscillatorSignalCrossScreen(StochasticFullOscillatorSignalCrossCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		StochasticOscillatorCurve curves = OspreyQuantMath.stochasticOscillatorSmaCurves(criteria.getPeriodLookBack(),
				criteria.getPeriodK(), criteria.getPeriodD(), criteria.getRange(), sqc.getHistoricalQuotes());

		boolean cross = curves.checkSignalCross(0, criteria.getRange(), criteria.getDirection().getCode());

		passed = cross;

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
