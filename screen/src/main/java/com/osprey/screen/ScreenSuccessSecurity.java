package com.osprey.screen;

import java.util.ArrayList;
import java.util.List;

import com.osprey.securitymaster.Security;
import com.osprey.trade.option.OptionStrategy;

public class ScreenSuccessSecurity {

	private final Security security;
	private List<OptionStrategy> strategies = new ArrayList<>();
	private List<String> namedScreenSets = new ArrayList<>();

	public ScreenSuccessSecurity(Security s) {
		this.security = s;
	}

	public void addScreen(String screen) {
		getNamedScreenSets().add(screen);
	}

	public void addStrategy(OptionStrategy os) {
		strategies.add(os);
	}

	public List<OptionStrategy> getStrategies() {
		return strategies;
	}

	public void setStrategies(List<OptionStrategy> strategies) {
		this.strategies = strategies;
	}

	public Security getSecurity() {
		return security;
	}

	public List<String> getNamedScreenSets() {
		return namedScreenSets;
	}

	public void setNamedScreenSets(List<String> namedScreenSets) {
		this.namedScreenSets = namedScreenSets;
	}

	public void addAllScreens(List<String> screens) {
		getNamedScreenSets().addAll(screens);
	}

	public void addAllStrategies(List<OptionStrategy> oss) {
		strategies.addAll(oss);
	}
}
