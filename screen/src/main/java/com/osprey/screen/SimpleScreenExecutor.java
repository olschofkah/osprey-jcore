package com.osprey.screen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.osprey.securitymaster.SecurityKey;

public class SimpleScreenExecutor implements IStockScreenPlanExecutor {

	private Set<SecurityKey> resultSet = new HashSet<>();
	private List<ScreenPlan> plans;

	@Override
	public void setPlans(Collection<ScreenPlan> plans) {
		this.plans = new ArrayList<>(plans);
	}

	@Override
	public void execute() {
		for (ScreenPlan plan : plans) {

			if (plan.execute().passed()) {
				resultSet.add(plan.getSecurityQuoteContainer().getKey());
			}

		}
	}

	public Set<SecurityKey> getResultSet() {
		return resultSet;
	}

}
