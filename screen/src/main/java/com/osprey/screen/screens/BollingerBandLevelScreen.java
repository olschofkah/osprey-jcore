package com.osprey.screen.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InvalidPeriodException;
import com.osprey.math.result.BollingerBandTimeSeries;
import com.osprey.screen.criteria.BollingerBandLevelCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class BollingerBandLevelScreen extends NumericalRelationalComparisonStockScreen {
	final static Logger logger = LogManager.getLogger(BollingerBandLevelScreen.class);

	private final BollingerBandLevelCriteria criteria;

	public BollingerBandLevelScreen(BollingerBandLevelCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		BollingerBandTimeSeries bollingerBands = OspreyQuantMath.bollingerBands(criteria.getPeriod(),
				criteria.getMultiplier(), criteria.getRange(), sqc.getHistoricalQuotes(), criteria.getMaType());

		try {

			double currentLevel;
			for (int i = 0; i < criteria.getRange(); ++i) {
				currentLevel = bollingerBands.getBandValue(i, criteria.getBand());

				compare(sqc.getHistoricalQuotes().get(i).getClose(), currentLevel);

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
