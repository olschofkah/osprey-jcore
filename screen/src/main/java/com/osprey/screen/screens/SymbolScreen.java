package com.osprey.screen.screens;

import com.osprey.screen.criteria.SymbolCriteria;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class SymbolScreen implements IStockScreen {

	private final SymbolCriteria criteria;
	private boolean passed;

	public SymbolScreen(SymbolCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public IStockScreen doScreen(SecurityQuoteContainer sqc) {

		passed = criteria.isContains() ? criteria.getSymbols().contains(sqc.getKey().getSymbol())
				: !criteria.getSymbols().contains(sqc.getKey().getSymbol());

		return this;
	}

	public boolean passed() {
		return passed;
	}

}
