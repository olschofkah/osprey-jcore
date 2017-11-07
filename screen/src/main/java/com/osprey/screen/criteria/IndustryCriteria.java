package com.osprey.screen.criteria;

import java.util.Set;

import com.osprey.screen.criteria.constants.ScreenType;

public class IndustryCriteria implements IScreenCriteria {

	private ScreenType type;
	private Set<String> industries;
	private boolean contains;

	public IndustryCriteria() {

	}

	public IndustryCriteria(Set<String> industry, boolean contains) {
		type = ScreenType.INDUSTRY;
		this.industries = industry;
		this.contains = contains;
	}

	public ScreenType getType() {
		return type;
	}

	public Set<String> getIndustries() {
		return industries;
	}

	public boolean isContains() {
		return contains;
	}

}
