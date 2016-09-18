package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InvalidPeriodException;
import com.osprey.math.result.BollingerBandTimeSeries;
import com.osprey.screen.criteria.BollingerBandLevelCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class BollingerBandLevelScreen implements IStockScreen {
	final static Logger logger = LogManager.getLogger(BollingerBandLevelScreen.class);

	private final BollingerBandLevelCriteria criteria;

	private boolean passed;

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

				switch (criteria.getRelationalOperator()) {
				case _EQ:
					passed = new BigDecimal(sqc.getHistoricalQuotes().get(i).getClose())
							.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
							.compareTo(new BigDecimal(currentLevel).setScale(OspreyConstants.PRICE_SCALE,
									RoundingMode.HALF_UP)) == 0;
					break;
				case _GE:
					passed = new BigDecimal(sqc.getHistoricalQuotes().get(i).getClose())
							.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
							.compareTo(new BigDecimal(currentLevel).setScale(OspreyConstants.PRICE_SCALE,
									RoundingMode.HALF_UP)) >= 0;
					break;
				case _GT:
					passed = sqc.getHistoricalQuotes().get(i).getClose() > currentLevel;
					break;
				case _LE:
					passed = new BigDecimal(sqc.getHistoricalQuotes().get(i).getClose())
							.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)
							.compareTo(new BigDecimal(currentLevel).setScale(OspreyConstants.PRICE_SCALE,
									RoundingMode.HALF_UP)) <= 0;
					break;
				case _LT:
					passed = sqc.getHistoricalQuotes().get(i).getClose() < currentLevel;
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
