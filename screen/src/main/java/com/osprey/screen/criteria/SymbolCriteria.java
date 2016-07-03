package com.osprey.screen.criteria;

import java.util.Set;

import com.osprey.screen.criteria.constants.ScreenType;

public class SymbolCriteria implements IStockScreenCriteria {

	private ScreenType type;
	private Set<String> symbols;
	private boolean contains;

	public SymbolCriteria() {

	}

	public SymbolCriteria(Set<String> symbols, boolean contains) {
		type = ScreenType.SYMBOL;
		this.symbols = symbols;
		this.contains = contains;
	}

	public ScreenType getType() {
		return type;
	}

	public Set<String> getSymbols() {
		return symbols;
	}

	public boolean isContains() {
		return contains;
	}

}
