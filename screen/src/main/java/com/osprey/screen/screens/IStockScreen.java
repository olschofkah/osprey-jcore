package com.osprey.screen.screens;

import java.util.List;

import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalQuote;

public interface IStockScreen {

	public IStockScreen doScreen(FundamentalPricedSecurity s, List<HistoricalQuote> h);

	public boolean passed();
}
