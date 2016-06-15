package com.osprey.screen.screens;

import java.util.List;

import com.osprey.screen.criteria.InstrumentTypeCriteria;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;

public class InstrumentTypeScreen implements IStockScreen {

	private final InstrumentTypeCriteria criteria;
	private boolean passed;

	public InstrumentTypeScreen(InstrumentTypeCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(FundamentalPricedSecurity s, List<HistoricalSecurity> h) {

		passed = criteria.isContains() ? criteria.getInstrumentTypes().contains(s.getInstrumentType())
				: !criteria.getInstrumentTypes().contains(s.getInstrumentType());

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
