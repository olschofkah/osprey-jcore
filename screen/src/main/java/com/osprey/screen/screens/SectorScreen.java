package com.osprey.screen.screens;

import com.osprey.screen.criteria.SectorCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class SectorScreen implements IStockScreen {

	private final SectorCriteria criteria;
	private boolean passed;

	public SectorScreen(SectorCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		passed = criteria.isContains() ? criteria.getSectors().contains(sqc.getSecurity().getSector())
				: !criteria.getSectors().contains(sqc.getSecurity().getSector());

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
