package com.osprey.screen;

import java.util.List;

import com.osprey.screen.criteria.StockScreenCriteriaGenerator;

public class StockScreenContainer {
	private String screenName;
	private List<StockScreenCriteriaGenerator> screenCriteria;

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public List<StockScreenCriteriaGenerator> getScreenCriteria() {
		return screenCriteria;
	}

	public void setScreenCriteria(List<StockScreenCriteriaGenerator> screens) {
		this.screenCriteria = screens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((screenName == null) ? 0 : screenName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockScreenContainer other = (StockScreenContainer) obj;
		if (screenName == null) {
			if (other.screenName != null)
				return false;
		} else if (!screenName.equals(other.screenName))
			return false;
		return true;
	}

}
