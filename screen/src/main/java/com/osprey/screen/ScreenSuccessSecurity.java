package com.osprey.screen;

import java.util.ArrayList;
import java.util.List;

import com.osprey.securitymaster.SecurityKey;
import com.osprey.trade.option.OptionStrategy;

public class ScreenSuccessSecurity {

	private final SecurityKey key;
	private List<OptionStrategy> strategies = new ArrayList<>();
	private List<String> namedScreenSets = new ArrayList<>();

	public ScreenSuccessSecurity(SecurityKey s) {
		this.key = s;
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

	public SecurityKey getKey() {
		return key;
	}
}
