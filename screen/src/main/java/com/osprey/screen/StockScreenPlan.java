package com.osprey.screen;

import java.util.List;
import java.util.Stack;

import com.osprey.screen.screens.IStockScreen;
import com.osprey.securitymaster.ExtendedPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;

public class StockScreenPlan {

	private Stack<IStockScreen> screens;
	private final ExtendedPricedSecurity security;
	private final List<HistoricalSecurity> history;

	private boolean passed = true;

	public StockScreenPlan(ExtendedPricedSecurity security, List<HistoricalSecurity> history) {
		this.security = security;
		this.history = history;
	}

	public StockScreenPlan add(IStockScreen screen) {
		screens.push(screen);
		return this;
	}

	public StockScreenPlan removePrevious() {
		screens.pop();
		return this;
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

	public ExtendedPricedSecurity getSecurity() {
		return security;
	}
}
