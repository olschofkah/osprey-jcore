package com.osprey.screen.criteria;

import java.util.Set;

import com.osprey.securitymaster.constants.InstrumentType;

public class InstrumentTypeCriteria implements IStockScreenCriteria {

	private StockScreenType type;
	private Set<InstrumentType> instrumentTypes;
	private boolean contains;

	public InstrumentTypeCriteria() {

	}

	public InstrumentTypeCriteria(Set<InstrumentType> types, boolean contains) {
		type = StockScreenType.INSTRUMENT_TYPE;
		this.instrumentTypes = types;
		this.contains = contains;
	}

	public StockScreenType getType() {
		return type;
	}

	public Set<InstrumentType> getInstrumentTypes() {
		return instrumentTypes;
	}

	public boolean isContains() {
		return contains;
	}

}
