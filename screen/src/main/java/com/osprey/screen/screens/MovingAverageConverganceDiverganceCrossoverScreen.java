package com.osprey.screen.screens;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.MovingAverageConverganceDiverganceCrossoverCriteria;
import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class MovingAverageConverganceDiverganceCrossoverScreen implements IStockScreen {
	final static Logger logger = LogManager.getLogger(MovingAverageConverganceDiverganceCrossoverScreen.class);

	private final MovingAverageConverganceDiverganceCrossoverCriteria criteria;

	private boolean passed;

	public MovingAverageConverganceDiverganceCrossoverScreen(
			MovingAverageConverganceDiverganceCrossoverCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double macd;
		double macdSignal;
		int previousComp = 2;
		int comp;
		boolean isAboveToBelow = criteria.getDirection() == CrossDirection.FROM_ABOVE_TO_BELOW;

		Map<String, List<Double>> macdCurves = OspreyQuantMath.macdCurves(criteria.getFastPeriod(),
				criteria.getSlowPeriod(), criteria.getSignalPeriod(), sqc.getHistoricalQuotes());

		List<Double> macdCurve = macdCurves.get("macd");
		List<Double> macdSignalCurve = macdCurves.get("macdSignal");

		for (int i = criteria.getRange(); i >= 0; --i) {

			macd = macdCurve.get(i);
			macdSignal = macdSignalCurve.get(i);

			comp = macd > macdSignal ? 1 : (macd < macdSignal ? -1 : 0);

			if (previousComp == 2) {
				previousComp = comp;
				continue;
			}

			if (((isAboveToBelow && previousComp == 1) || (!isAboveToBelow && previousComp == -1))
					&& previousComp != comp) {
				passed = true;
				break;
			}

			previousComp = comp;
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
