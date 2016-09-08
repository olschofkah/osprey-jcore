package com.osprey.marketdata.feed.ychart.pojo;

public enum YChartEventGroup {

	DIVIDENDS("dividends"),
	EARNINGS("earnings"),
	MISC("misc"),
	SPLITS("splits"),
	SPINOFFS("spinoffs"),
	OTHER("");
	
	private final String value;

	private YChartEventGroup(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static YChartEventGroup fromValue(String value) {
		for (YChartEventGroup eventGroup : values()) {
			if (eventGroup.value.equals(value)) {
				return eventGroup;
			}
		}
		return null;
	}

}
