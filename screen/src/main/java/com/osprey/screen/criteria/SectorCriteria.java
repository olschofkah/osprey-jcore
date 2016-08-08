package com.osprey.screen.criteria;

import java.util.Set;

import com.osprey.screen.criteria.constants.ScreenType;

public class SectorCriteria implements IScreenCriteria {

	private ScreenType type;
	private Set<String> sectors;
	private boolean contains;

	public SectorCriteria() {

	}

	public SectorCriteria(Set<String> sectors, boolean contains) {
		type = ScreenType.SECTOR;
		this.sectors = sectors;
		this.contains = contains;
	}

	public ScreenType getType() {
		return type;
	}

	public Set<String> getSectors() {
		return sectors;
	}

	public boolean isContains() {
		return contains;
	}

}
