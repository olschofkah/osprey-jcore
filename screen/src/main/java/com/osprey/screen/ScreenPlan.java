package com.osprey.screen;

import java.util.Stack;

import com.osprey.screen.screens.IStockScreen;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class ScreenPlan {

	private Stack<IStockScreen> screens;
	private final SecurityQuoteContainer sqc;

	private boolean passed = true;

	public ScreenPlan(SecurityQuoteContainer quoteContainer) {
		this.sqc = quoteContainer;
		screens = new Stack<>();
	}

	public ScreenPlan add(IStockScreen screen) {
		screens.push(screen);
		return this;
	}

	public IStockScreen removePrevious() {
		return screens.pop();
	}

	public ScreenPlan execute() {

		IStockScreen lastScreen;
		while (passed && !screens.isEmpty()) {
			lastScreen = screens.pop().doScreen(sqc);
			passed = lastScreen.passed();
		}

		return this;
	}

	public boolean passed() {
		return passed;
	}

	public SecurityQuoteContainer getSecurityQuoteContainer() {
		return sqc;
	}
}
