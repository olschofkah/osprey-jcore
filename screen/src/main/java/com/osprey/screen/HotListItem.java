package com.osprey.screen;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.osprey.securitymaster.SecurityKey;
import com.osprey.trade.option.OptionStrategy;

public class HotListItem {

	private SecurityKey key;
	private Date reportDate;
	private int recentCount;
	private List<OptionStrategy> strategies = new ArrayList<>();
	private List<ModelSymbolStatistic> models = new ArrayList<>();

	public HotListItem() {
		// for ObjectMapping Only
	}

	public HotListItem(SecurityKey s) {
		this.key = s;
	}

	public void addModel(String model) {
		models.add(new ModelSymbolStatistic(0, model));
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

	public List<ModelSymbolStatistic> getModels() {
		return models;
	}

	public void setModels(List<ModelSymbolStatistic> models) {
		this.models = models;
	}

	public void addAllScreens(List<ModelSymbolStatistic> screens) {
		getModels().addAll(screens);
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
		return (Date) reportDate.clone();
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = (Date) reportDate.clone();
	}

	public int getRecentCount() {
		return recentCount;
	}

	public void setRecentCount(int recentCount) {
		this.recentCount = recentCount;
	}

	public void addModelCounts(Map<String, Integer> modelStatMap) {
		for (ModelSymbolStatistic stat : models) {
			if (modelStatMap.containsKey(stat.getModelName())) {
				stat.setRecentOccurrence(stat.getRecentOccurrence() + modelStatMap.get(stat.getModelName()));
			}
		}
	}
}
