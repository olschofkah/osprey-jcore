package com.osprey.securitymaster;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SecurityQuoteContainer {

	private final SecurityKey key;
	private Security security;
	private SecurityQuote securityQuote;
	private List<HistoricalQuote> historicalQuotes;
	private FundamentalQuote fundamentalQuote;

	private List<SecurityEvent> events;
	private SecurityUpcomingEvents upcomingEvents;

	public SecurityQuoteContainer(SecurityKey key) {
		this.key = key;
	}

	public SecurityQuoteContainer(SecurityKey key, List<HistoricalQuote> historicalQuotes) {
		this(key);
		this.historicalQuotes = historicalQuotes;
	}

	public SecurityQuoteContainer(SecurityKey key, List<HistoricalQuote> historicalQuotes,
			FundamentalQuote fundamentalQuote) {
		this(key, historicalQuotes);
		this.fundamentalQuote = fundamentalQuote;
	}

	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}

	public SecurityQuote getSecurityQuote() {
		return securityQuote;
	}

	public void setSecurityQuote(SecurityQuote securityQuote) {
		this.securityQuote = securityQuote;
	}

	public List<HistoricalQuote> getHistoricalQuotes() {
		return historicalQuotes;
	}

	public void setHistoricalQuotes(List<HistoricalQuote> historicalQuotes) {
		this.historicalQuotes = historicalQuotes;
	}

	public FundamentalQuote getFundamentalQuote() {
		return fundamentalQuote;
	}

	public void setFundamentalQuote(FundamentalQuote fundamentalQuote) {
		this.fundamentalQuote = fundamentalQuote;
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
		SecurityQuoteContainer other = (SecurityQuoteContainer) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	public List<SecurityEvent> getEvents() {
		return events;
	}

	public void setEvents(List<SecurityEvent> events) {
		this.events = events;
	}

	public SecurityUpcomingEvents getUpcomingEvents() {
		return upcomingEvents;
	}

	public void setUpcomingEvents(SecurityUpcomingEvents upcomingEvents) {
		this.upcomingEvents = upcomingEvents;
	}

	public void timestamp() {
		ZonedDateTime now = ZonedDateTime.now();

		if (security != null) {
			security.setTimestamp(now);
		}

		if (securityQuote != null) {
			securityQuote.setTimestamp(now);
		}

		if (fundamentalQuote != null) {
			fundamentalQuote.setTimestamp(now);
		}

		if (upcomingEvents != null) {
			upcomingEvents.setTimestamp(now);
		}

		// timestamps on lists are set during creation for performance;
	}

	public void sortEventsDescending() {
		// sort the events by date ...
		Collections.sort(events, new Comparator<SecurityEvent>() {

			@Override
			public int compare(SecurityEvent o1, SecurityEvent o2) {
				return o2.getDate().compareTo(o1.getDate());
			}

		});
	}
}
