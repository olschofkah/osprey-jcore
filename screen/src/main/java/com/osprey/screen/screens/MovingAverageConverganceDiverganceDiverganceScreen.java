package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.MovingAverageConverganceDiverganceDiverganceCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class MovingAverageConverganceDiverganceDiverganceScreen implements IStockScreen {
	final static Logger logger = LogManager.getLogger(MovingAverageConverganceDiverganceDiverganceScreen.class);

	private final MovingAverageConverganceDiverganceDiverganceCriteria criteria;

	private boolean passed;

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

			switch (criteria.getRelationalOperator()) {
			case _EQ:
				passed = new BigDecimal(divergance).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getDivergance()).setScale(OspreyConstants.PRICE_SCALE,
								RoundingMode.HALF_UP)) == 0;
				break;
			case _GE:
				passed = new BigDecimal(divergance).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getDivergance()).setScale(OspreyConstants.PRICE_SCALE,
								RoundingMode.HALF_UP)) >= 0;
				break;
			case _GT:
				passed = divergance > criteria.getDivergance();
				break;
			case _LE:
				passed = new BigDecimal(divergance).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
						.compareTo(new BigDecimal(criteria.getDivergance()).setScale(OspreyConstants.PRICE_SCALE,
								RoundingMode.HALF_UP)) <= 0;
				break;
			case _LT:
				passed = divergance < criteria.getDivergance();
				break;
			default:
				passed = false;
				break;

			}

			if (passed) {
				break;
			}
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
