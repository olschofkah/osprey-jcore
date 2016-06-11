package com.osprey.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BasicStockScreenExecutor implements IStockScreenPlanExecutor {

	private Set<String> resultSet;
	private List<StockScreenPlan> plans;

	@Override
	public void setPlans(List<StockScreenPlan> plans) {
		this.plans = new ArrayList<>(plans);
	}

	@Override
	public void execute() {
		for (StockScreenPlan plan : plans) {

			if (plan.execute().passed()) {
				resultSet.add(plan.getSecurity().getTicker());
			}

		}
	}

	public Set<String> getResultSet() {
		return resultSet;
	}

}
