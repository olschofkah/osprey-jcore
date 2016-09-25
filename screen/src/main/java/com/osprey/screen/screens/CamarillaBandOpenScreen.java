package com.osprey.screen.screens;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.CamarillaBandOpenCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OspreyConstants;

public class CamarillaBandOpenScreen implements IStockScreen {

	private final CamarillaBandOpenCriteria criteria;

	private boolean passed;

	public CamarillaBandOpenScreen(CamarillaBandOpenCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double[] camarillaBands = OspreyQuantMath.camarillaBands(criteria.getPeriod(), 0, sqc.getHistoricalQuotes());

		// this is populated with the current quote for market hours ...
		// with 'close' being last.
		double open = sqc.getHistoricalQuotes().get(0).getOpen();
		BigDecimal openBd = new BigDecimal(open).setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP);

		boolean passCriteria1 = false;
		switch (criteria.getRelationalOperator1()) {
		case _EQ:
			passCriteria1 = openBd.compareTo(new BigDecimal(camarillaBands[criteria.getBandIndex1()])
					.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
			break;
		case _GE:
			passCriteria1 = openBd.compareTo(new BigDecimal(camarillaBands[criteria.getBandIndex1()])
					.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
			break;
		case _GT:
			passCriteria1 = open > camarillaBands[criteria.getBandIndex1()];
			break;
		case _LE:
			passCriteria1 = openBd.compareTo(new BigDecimal(camarillaBands[criteria.getBandIndex1()])
					.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
			break;
		case _LT:
			passCriteria1 = open < camarillaBands[criteria.getBandIndex1()];
			break;
		default:
			passCriteria1 = false;
			break;

		}

		if (passCriteria1) {
			switch (criteria.getRelationalOperator2()) {
			case _EQ:
				passed = openBd.compareTo(new BigDecimal(camarillaBands[criteria.getBandIndex2()])
						.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) == 0;
				break;
			case _GE:
				passed = openBd.compareTo(new BigDecimal(camarillaBands[criteria.getBandIndex2()])
						.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) >= 0;
				break;
			case _GT:
				passed = open > camarillaBands[criteria.getBandIndex2()];
				break;
			case _LE:
				passed = openBd.compareTo(new BigDecimal(camarillaBands[criteria.getBandIndex2()])
						.setScale(OspreyConstants.PRICE_SCALE, RoundingMode.HALF_UP)) <= 0;
				break;
			case _LT:
				passed = open < camarillaBands[criteria.getBandIndex2()];
				break;
			default:
				passed = false;
				break;

			}
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
