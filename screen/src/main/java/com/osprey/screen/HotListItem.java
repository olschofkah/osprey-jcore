package com.osprey.screen;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.osprey.securitymaster.SecurityKey;
import com.osprey.trade.option.OptionStrategy;

public class HotListItem {

	private SecurityKey key;
	private Date reportDate;
	private List<OptionStrategy> strategies = new ArrayList<>();
	private List<String> namedScreenSets = new ArrayList<>();

	public HotListItem() {
		// for ObjectMapping Only
	}

	public HotListItem(SecurityKey s) {
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	public void setKey(SecurityKey key) {
		this.key = key;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
}
