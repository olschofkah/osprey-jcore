package com.osprey.securitymaster;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.osprey.securitymaster.constants.SecurityEventType;

public class SecurityEvent {

	private final SecurityKey key;
	private final LocalDate date;
	private final LocalTime time;
	private final SecurityEventType event;

	private final double amount;
	private final String description;

	private final ZonedDateTime timestamp;

	public SecurityEvent(SecurityKey key, LocalDate date, LocalTime time, SecurityEventType event, double amount,
			String description, ZonedDateTime timestamp) {
		this.key = key;
		this.date = date;
		this.time = time;
		this.event = event;
		this.amount = amount;
		this.description = description;
		this.timestamp = timestamp;
	}

	public SecurityKey getKey() {
		return key;
	}

	public LocalDate getDate() {
		return date;
	}

	public SecurityEventType getEvent() {
		return event;
	}

	public double getAmount() {
		return amount;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
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
		SecurityEvent other = (SecurityEvent) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (event != other.event)
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

	public String getDescription() {
		return description;
	}

	public LocalTime getTime() {
		return time;
	}
}
