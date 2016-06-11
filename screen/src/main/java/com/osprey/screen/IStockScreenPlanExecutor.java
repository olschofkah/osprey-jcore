package com.osprey.screen;

import java.util.List;

public interface IStockScreenPlanExecutor {

	public void setPlans(List<StockScreenPlan> plans);

	public void execute();

}
