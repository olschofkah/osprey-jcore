package com.osprey.securitymaster;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SecurityQuoteContainer {

	private final SecurityKey key;
	private Security security;
	private SecurityQuote securityQuote;
	private List<HistoricalQuote> historicalQuotes;
	private FundamentalQuote fundamentalQuote;
	private EnhancedSecurity enhancedSecurity;

	public SecurityQuoteContainer(SecurityKey key) {
		this.key = key;
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

	public EnhancedSecurity getEnhancedSecurity() {
		return enhancedSecurity;
	}

	public void setEnhancedSecurity(EnhancedSecurity enhancedSecurity) {
		this.enhancedSecurity = enhancedSecurity;
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
}
