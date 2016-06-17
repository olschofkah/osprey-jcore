package com.osprey.screen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicScreenExecutor implements IStockScreenPlanExecutor {

	private Set<String> resultSet = new HashSet<>();
	private List<ScreenPlan> plans;

	@Override
	public void setPlans(Collection<ScreenPlan> plans) {
		this.plans = new ArrayList<>(plans);
	}

	@Override
	public void execute() {
		for (ScreenPlan plan : plans) {

			if (plan.execute().passed()) {
				resultSet.add(plan.getSecurity().getTicker());
			}

		}
	}

	public Set<String> getResultSet() {
		return resultSet;
	}

}
