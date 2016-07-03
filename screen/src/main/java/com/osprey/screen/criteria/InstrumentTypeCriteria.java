package com.osprey.screen.criteria;

import java.util.Set;

import com.osprey.screen.criteria.constants.ScreenType;
import com.osprey.securitymaster.constants.InstrumentType;

public class InstrumentTypeCriteria implements IScreenCriteria {

	private ScreenType type;
	private Set<InstrumentType> instrumentTypes;
	private boolean contains;

	public InstrumentTypeCriteria() {

	}

	public InstrumentTypeCriteria(Set<InstrumentType> types, boolean contains) {
		type = ScreenType.INSTRUMENT_TYPE;
		this.instrumentTypes = types;
		this.contains = contains;
	}

	public ScreenType getType() {
		return type;
	}

	public Set<InstrumentType> getInstrumentTypes() {
		return instrumentTypes;
	}

	public boolean isContains() {
		return contains;
	}

}
