package com.osprey.securitymaster;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class HistoricalQuote {

	private final SecurityKey key;
	private final LocalDate historicalDate;

	private double open;
	private double close;
	private double high;
	private double low;
	private long volume;
	private double adjClose;

	private ZonedDateTime timestamp;

	public HistoricalQuote(String ticker, LocalDate historicalDate) {
		this.key = new SecurityKey(ticker, null);
		this.historicalDate = historicalDate;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public double getAdjClose() {
		return adjClose;
	}

	public void setAdjClose(double adjClose) {
		this.adjClose = adjClose;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public SecurityKey getKey() {
		return key;
	}

	public LocalDate getHistoricalDate() {
		return historicalDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((historicalDate == null) ? 0 : historicalDate.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoricalQuote other = (HistoricalQuote) obj;
		if (historicalDate == null) {
			if (other.historicalDate != null)
				return false;
		} else if (!historicalDate.equals(other.historicalDate))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
