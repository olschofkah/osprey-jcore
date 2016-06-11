package com.osprey.screen;

import java.util.Collection;

public interface IStockScreenPlanExecutor {

	public void setPlans(Collection<StockScreenPlan> plans);

	public void execute();

}
