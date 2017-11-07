package com.osprey.screen.screens;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.math3.util.Pair;

import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.MomentumCriteria;
import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class MomentumScreen implements IStockScreen {

	private final MomentumCriteria criteria;

	private boolean passed;

	public MomentumScreen(MomentumCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		double momentum = 0.0;
		int previousComp = 2;
		int comp;
		boolean isAboveToBelow = criteria.getDirection() == CrossDirection.FROM_ABOVE_TO_BELOW;

		List<Pair<LocalDate, Double>> curve = OspreyQuantMath.momentumCurve(criteria.getPeriod1(),
				sqc.getHistoricalQuotes());

		int adjRange = criteria.getRange() > curve.size() ? curve.size() : criteria.getRange();

		for (int offset = adjRange - 1; offset >= 0; --offset) {

			momentum = curve.get(offset).getValue();

			comp = momentum > criteria.getCrossoverPoint() ? 1 : (momentum < criteria.getCrossoverPoint() ? -1 : 0);

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
