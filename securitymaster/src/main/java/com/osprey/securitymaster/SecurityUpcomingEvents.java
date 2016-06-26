package com.osprey.securitymaster;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SecurityUpcomingEvents {

	private final SecurityKey key;

	private ZonedDateTime timestamp;

	private LocalDate nextEarningsDateEstLow;
	private LocalDate nextEarningsDateEstHigh;
	private LocalDate nextDivDate;
	private LocalDate nextExDivDate;
	private LocalDate nextRevenue;

	public SecurityUpcomingEvents(String symbol) {
		this(new SecurityKey(symbol, null));
	}

	public SecurityUpcomingEvents(SecurityKey key) {
		this.key = key;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public LocalDate getNextEarningsDateEstLow() {
		return nextEarningsDateEstLow;
	}

	public void setNextEarningsDateEstLow(LocalDate nextEarningsDateEstLow) {
		this.nextEarningsDateEstLow = nextEarningsDateEstLow;
	}

	public LocalDate getNextEarningsDateEstHigh() {
		return nextEarningsDateEstHigh;
	}

	public void setNextEarningsDateEstHigh(LocalDate nextEarningsDateEstHigh) {
		this.nextEarningsDateEstHigh = nextEarningsDateEstHigh;
	}

	public LocalDate getNextDivDate() {
		return nextDivDate;
	}

	public void setNextDivDate(LocalDate nextDivDate) {
		this.nextDivDate = nextDivDate;
	}

	public LocalDate getNextExDivDate() {
		return nextExDivDate;
	}

	public void setNextExDivDate(LocalDate nextExDivDate) {
		this.nextExDivDate = nextExDivDate;
	}

	public LocalDate getNextRevenue() {
		return nextRevenue;
	}

	public void setNextRevenue(LocalDate nextRevenue) {
		this.nextRevenue = nextRevenue;
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
		SecurityUpcomingEvents other = (SecurityUpcomingEvents) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

}
