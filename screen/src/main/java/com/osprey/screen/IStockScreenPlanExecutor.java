package com.osprey.screen;

import java.util.Collection;

public interface IStockScreenPlanExecutor {

	public void setPlans(Collection<ScreenPlan> plans);

	public void execute();

}
