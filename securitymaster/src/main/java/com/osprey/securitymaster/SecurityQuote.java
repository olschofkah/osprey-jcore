package com.osprey.securitymaster;

import java.time.ZonedDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SecurityQuote {

	private final SecurityKey key;

	private double last;
	private double close;
	private double open;
	private double bid;
	private long bidSize;
	private double ask;
	private long askSize;

	private long volume;
	private double high;
	private double low;

	private long openInterest;

	private String dataCurrency;

	private ZonedDateTime timestamp;

	public SecurityQuote(String symbol) {
		this.key = new SecurityKey(symbol, null);
	}

	public SecurityQuote(SecurityKey key) {
		this.key = key;
	}

	public double getLast() {
		return last;
	}

	public void setLast(double last) {
		this.last = last;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getBid() {
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}

	public long getBidSize() {
		return bidSize;
	}

	public void setBidSize(long bidSize) {
		this.bidSize = bidSize;
	}

	public double getAsk() {
		return ask;
	}

	public void setAsk(double ask) {
		this.ask = ask;
	}

	public long getAskSize() {
		return askSize;
	}

	public void setAskSize(long askSize) {
		this.askSize = askSize;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
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

	public long getOpenInterest() {
		return openInterest;
	}

	public void setOpenInterest(long openInterest) {
		this.openInterest = openInterest;
	}

	public String getDataCurrency() {
		return dataCurrency;
	}

	public void setDataCurrency(String dataCurrency) {
		this.dataCurrency = dataCurrency;
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		SecurityQuote other = (SecurityQuote) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
}
