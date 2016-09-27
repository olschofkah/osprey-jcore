package com.osprey.screen.screens;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.MovingAverageConverganceDiverganceDiverganceCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class MovingAverageConverganceDiverganceDiverganceScreen extends NumericalRelationalComparisonStockScreen {
	final static Logger logger = LogManager.getLogger(MovingAverageConverganceDiverganceDiverganceScreen.class);

	private final MovingAverageConverganceDiverganceDiverganceCriteria criteria;

	public MovingAverageConverganceDiverganceDiverganceScreen(
			MovingAverageConverganceDiverganceDiverganceCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double macd;
		double macdSignal;

		Map<String, List<Double>> macdCurves = OspreyQuantMath.macdCurves(criteria.getFastPeriod(),
				criteria.getSlowPeriod(), criteria.getSignalPeriod(), sqc.getHistoricalQuotes());

		List<Double> macdCurve = macdCurves.get("macd");
		List<Double> macdSignalCurve = macdCurves.get("macdSignal");

		double divergance = 0;

		for (int i = criteria.getRange(); i >= 0; --i) {

			macd = macdCurve.get(i);
			macdSignal = macdSignalCurve.get(i);

			divergance = macd - macdSignal;

			compare(divergance, criteria.getDivergance());

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
