package com.osprey.securitymaster;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SecurityKey {

	private final String symbol;
	private final String cusip;

	public SecurityKey(String symbol, String cusip) {
		super();
		this.symbol = symbol;
		this.cusip = cusip;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getCusip() {
		return cusip;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cusip == null) ? 0 : cusip.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
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
		SecurityKey other = (SecurityKey) obj;
		if (cusip == null) {
			if (other.cusip != null)
				return false;
		} else if (!cusip.equals(other.cusip))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
