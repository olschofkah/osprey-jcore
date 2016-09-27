package com.osprey.screen.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InvalidPeriodException;
import com.osprey.math.result.StochasticOscillatorTimeSeries;
import com.osprey.screen.criteria.StochasticFullOscillatorLevelCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class StochasticFullOscillatorLevelScreen extends NumericalRelationalComparisonStockScreen {
	final static Logger logger = LogManager.getLogger(StochasticFullOscillatorLevelScreen.class);

	private final StochasticFullOscillatorLevelCriteria criteria;

	public StochasticFullOscillatorLevelScreen(StochasticFullOscillatorLevelCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		StochasticOscillatorTimeSeries curves = OspreyQuantMath.stochasticOscillatorSmaCurves(
				criteria.getPeriodLookBack(), criteria.getPeriodK(), criteria.getPeriodD(), criteria.getRange(),
				sqc.getHistoricalQuotes());

		try {

			double currentLevel;
			for (int i = 0; i < criteria.getRange(); ++i) {
				currentLevel = curves.getSignalLevel(i);

				compare(currentLevel, criteria.getLevel());

				if (passed) {
					break;
				}
			}

		} catch (InvalidPeriodException e) {
			passed = false;
		}

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
