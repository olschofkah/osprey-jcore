package com.osprey.screen;

import java.util.List;
import java.util.Stack;

import com.osprey.screen.screens.IStockScreen;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;

public class StockScreenPlan {

	private Stack<IStockScreen> screens;
	private final FundamentalPricedSecurity security;
	private final List<HistoricalSecurity> history;

	private boolean passed = true;

	public StockScreenPlan(FundamentalPricedSecurity security, List<HistoricalSecurity> history) {
		this.security = security;
		this.history = history;

		screens = new Stack<>();
	}

	public StockScreenPlan add(IStockScreen screen) {
		screens.push(screen);
		return this;
	}

	public IStockScreen removePrevious() {
		return screens.pop();
	}

	public StockScreenPlan execute() {

		IStockScreen lastScreen;
		while (passed && !screens.isEmpty()) {
			lastScreen = screens.pop().doScreen(getSecurity(), history);
			passed = lastScreen.passed();
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

	public FundamentalPricedSecurity getSecurity() {
		return security;
	}
}
