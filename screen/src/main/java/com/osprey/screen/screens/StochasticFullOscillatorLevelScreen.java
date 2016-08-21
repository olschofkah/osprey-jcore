package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InvalidPeriodException;
import com.osprey.math.result.StochasticOscillatorCurve;
import com.osprey.screen.criteria.StochasticFullOscillatorLevelCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class StochasticFullOscillatorLevelScreen implements IStockScreen {
	final static Logger logger = LogManager.getLogger(StochasticFullOscillatorLevelScreen.class);

	private final StochasticFullOscillatorLevelCriteria criteria;

	private boolean passed;

	public StochasticFullOscillatorLevelScreen(StochasticFullOscillatorLevelCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		StochasticOscillatorCurve curves = OspreyQuantMath.stochasticOscillatorSmaCurves(criteria.getPeriodLookBack(),
				criteria.getPeriodK(), criteria.getPeriodD(), criteria.getRange(), sqc.getHistoricalQuotes());

		try {

			double currentLevel;
			for (int i = 0; i < criteria.getRange(); ++i) {
				currentLevel = curves.getSignalLevel(i);

				switch (criteria.getRelationalOperator()) {
				case _EQ:
					passed = new BigDecimal(currentLevel).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
							.compareTo(new BigDecimal(criteria.getLevel()).setScale(OspreyConstants.PRICE_SCALE,
									RoundingMode.HALF_UP)) == 0;
					break;
				case _GE:
					passed = new BigDecimal(currentLevel).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
							.compareTo(new BigDecimal(criteria.getLevel()).setScale(OspreyConstants.PRICE_SCALE,
									RoundingMode.HALF_UP)) >= 0;
					break;
				case _GT:
					passed = currentLevel > criteria.getLevel();
					break;
				case _LE:
					passed = new BigDecimal(currentLevel).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
							.compareTo(new BigDecimal(criteria.getLevel()).setScale(OspreyConstants.PRICE_SCALE,
									RoundingMode.HALF_UP)) <= 0;
					break;
				case _LT:
					passed = currentLevel < criteria.getLevel();
					break;
				default:
					passed = false;
					break;

				}

				if (passed) {
					break;
				}
			}
			
		} catch (InvalidPeriodException e) {
			passed = false;
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
