package com.osprey.securitymaster;

import java.time.ZonedDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.osprey.securitymaster.constants.EarningsReportTime;

public class HistoricalSecurity {

	private final String ticker;
	private final ZonedDateTime historicalDate;

	private double close;
	private double adjClose;
	private double open;
	private double high;
	private double low;
	private double volume;

	// TODO Add in estimated vs actual for earnings somewhere?

	private ZonedDateTime nextEarningsDate;
	private ZonedDateTime previousEarningsDate;
	private EarningsReportTime nextEarningsReportTime;
	private EarningsReportTime previousEarningsReportTime;

	private ZonedDateTime nextDivDate;
	private ZonedDateTime previousDivDate;

	private ZonedDateTime timestamp;

	public HistoricalSecurity(String ticker, ZonedDateTime historicalDate) {
		this.ticker = ticker;
		this.historicalDate = historicalDate;
	}

	public ZonedDateTime getNextEarningsDate() {
		return nextEarningsDate;
	}

	public void setNextEarningsDate(ZonedDateTime nextEarningsDate) {
		this.nextEarningsDate = nextEarningsDate;
	}

	public ZonedDateTime getPreviousEarningsDate() {
		return previousEarningsDate;
	}

	public void setPreviousEarningsDate(ZonedDateTime previousEarningsDate) {
		this.previousEarningsDate = previousEarningsDate;
	}

	public ZonedDateTime getNextDivDate() {
		return nextDivDate;
	}

	public void setNextDivDate(ZonedDateTime nextDivDate) {
		this.nextDivDate = nextDivDate;
	}

	public ZonedDateTime getPreviousDivDate() {
		return previousDivDate;
	}

	public void setPreviousDivDate(ZonedDateTime previousDivDate) {
		this.previousDivDate = previousDivDate;
	}

	public String getTicker() {
		return ticker;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getAdjClose() {
		return adjClose;
	}

	public void setAdjClose(double adjClose) {
		this.adjClose = adjClose;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
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

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public ZonedDateTime getHistoricalDate() {
		return historicalDate;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	public EarningsReportTime getNextEarningsReportTime() {
		return nextEarningsReportTime;
	}

	public void setNextEarningsReportTime(EarningsReportTime nextEarningsReportTime) {
		this.nextEarningsReportTime = nextEarningsReportTime;
	}

	public EarningsReportTime getPreviousEarningsReportTime() {
		return previousEarningsReportTime;
	}

	public void setPreviousEarningsReportTime(EarningsReportTime previousEarningsReportTime) {
		this.previousEarningsReportTime = previousEarningsReportTime;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((historicalDate == null) ? 0 : historicalDate.hashCode());
		result = prime * result + ((ticker == null) ? 0 : ticker.hashCode());
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
		HistoricalSecurity other = (HistoricalSecurity) obj;
		if (historicalDate == null) {
			if (other.historicalDate != null)
				return false;
		} else if (!historicalDate.equals(other.historicalDate))
			return false;
		if (ticker == null) {
			if (other.ticker != null)
				return false;
		} else if (!ticker.equals(other.ticker))
			return false;
		return true;
	}

}
