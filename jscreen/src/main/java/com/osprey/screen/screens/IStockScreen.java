package com.osprey.screen.screens;

import java.util.List;

import com.osprey.securitymaster.ExtendedPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;

public interface IStockScreen {

	public IStockScreen doScreen(ExtendedPricedSecurity s, List<HistoricalSecurity> h);

	public boolean passed();
}
