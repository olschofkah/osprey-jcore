package com.osprey.screen.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.math.exception.InvalidPeriodException;
import com.osprey.math.result.BollingerBandTimeSeries;
import com.osprey.screen.criteria.BollingerBandCrossCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class BollingerBandCrossScreen implements IStockScreen {
	final static Logger logger = LogManager.getLogger(BollingerBandCrossScreen.class);

	private final BollingerBandCrossCriteria criteria;

	private boolean passed;

	public BollingerBandCrossScreen(BollingerBandCrossCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		BollingerBandTimeSeries bollingerBands = OspreyQuantMath.bollingerBands(criteria.getPeriod(),
				criteria.getMultiplier(), criteria.getRange(), sqc.getHistoricalQuotes(), criteria.getMaType());

		try {
			passed = bollingerBands.checkBandCross(0, criteria.getRange(), criteria.getDirection().getCode(),
					criteria.getBand());
		} catch (InvalidPeriodException e) {
			passed = false;
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
