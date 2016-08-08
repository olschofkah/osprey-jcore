package com.osprey.screen.screens;

import org.apache.commons.lang3.StringUtils;

import com.osprey.screen.criteria.IndustryCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class IndustryScreen implements IStockScreen {

	private final IndustryCriteria criteria;
	private boolean passed;

	public IndustryScreen(IndustryCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		for (String industryPart : criteria.getIndustries()) {
			if (StringUtils.containsIgnoreCase(sqc.getSecurity().getIndustry(), industryPart)) {
				passed = true;
				break;
			}
		}
		
		if(!criteria.isContains()){
			passed = !passed;
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
