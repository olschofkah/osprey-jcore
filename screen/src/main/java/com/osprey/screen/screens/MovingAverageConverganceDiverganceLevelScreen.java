package com.osprey.screen.screens;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.MovingAverageConverganceDiverganceLevelCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class MovingAverageConverganceDiverganceLevelScreen extends NumericalRelationalComparisonStockScreen {
	final static Logger logger = LogManager.getLogger(MovingAverageConverganceDiverganceLevelScreen.class);

	private final MovingAverageConverganceDiverganceLevelCriteria criteria;

	public MovingAverageConverganceDiverganceLevelScreen(MovingAverageConverganceDiverganceLevelCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		Map<String, List<Double>> macdCurves = OspreyQuantMath.macdCurves(criteria.getFastPeriod(),
				criteria.getSlowPeriod(), criteria.getSignalPeriod(), sqc.getHistoricalQuotes());

		List<Double> macdSignalCurve = macdCurves.get("macdSignal");

		double macdSignal;
		for (int i = criteria.getRange(); i >= 0; --i) {

			macdSignal = macdSignalCurve.get(i);

			compare(macdSignal, criteria.getLevel());

			if (passed) {
				break;
			}
		}

		return this;
	}

	@Override
	protected RelationalOperator getRelationalOperator() {
		return criteria.getRelationalOperator();
	}
}
