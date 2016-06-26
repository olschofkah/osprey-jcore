package com.osprey.screen.screens;

import com.osprey.screen.criteria.InstrumentTypeCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class InstrumentTypeScreen implements IStockScreen {

	private final InstrumentTypeCriteria criteria;
	private boolean passed;

	public InstrumentTypeScreen(InstrumentTypeCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		passed = criteria.isContains() ? criteria.getInstrumentTypes().contains(sqc.getSecurity().getInstrumentType())
				: !criteria.getInstrumentTypes().contains(sqc.getSecurity().getInstrumentType());

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
