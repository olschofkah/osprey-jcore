package com.osprey.screen.screens;

import java.util.List;

import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;

public interface IStockScreen {

	public IStockScreen doScreen(FundamentalPricedSecurity s, List<HistoricalSecurity> h);

	public boolean passed();
}
