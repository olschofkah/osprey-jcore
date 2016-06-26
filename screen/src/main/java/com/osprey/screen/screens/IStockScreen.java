package com.osprey.screen.screens;

import com.osprey.securitymaster.SecurityQuoteContainer;

public interface IStockScreen {

	public IStockScreen doScreen(SecurityQuoteContainer quoteContainer);

	public boolean passed();
}
