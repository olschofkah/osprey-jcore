package com.osprey.securitymaster;

import java.time.ZonedDateTime;

public class HistoricalSecurity {

	private int securityId;
	private double closingPrice;
	private double minPrice;
	private double maxPrice;
	private double volume;
	private ZonedDateTime historicalDate;

	public int getSecurityId() {
		return securityId;
	}

	public void setSecurityId(int securityId) {
		this.securityId = securityId;
	}

	public double getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(double closingPrice) {
		this.closingPrice = closingPrice;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public ZonedDateTime getHistoricalDate() {
		return historicalDate;
	}

	public void setHistoricalDate(ZonedDateTime historicalDate) {
		this.historicalDate = historicalDate;
	}

}
