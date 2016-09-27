package com.osprey.screen.screens;

import com.osprey.securitymaster.SecurityQuoteContainer;

public interface IStockScreen {

	public abstract IStockScreen doScreen(SecurityQuoteContainer quoteContainer);

	public abstract boolean passed(); 
	
	
}
