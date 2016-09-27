package com.osprey.screen.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.MoneyFlowIndexLevelCriteria;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class MoneyFlowIndexLevelScreen extends NumericalRelationalComparisonStockScreen {
	final static Logger logger = LogManager.getLogger(MoneyFlowIndexLevelScreen.class);

	private final MoneyFlowIndexLevelCriteria criteria;

	public MoneyFlowIndexLevelScreen(MoneyFlowIndexLevelCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double currentLevel;
		for (int i = 0; i < criteria.getRange(); ++i) {
			currentLevel = OspreyQuantMath.moneyFlowIndex(criteria.getPeriod(), i, sqc.getHistoricalQuotes());

			compare(currentLevel, criteria.getLevel());

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
