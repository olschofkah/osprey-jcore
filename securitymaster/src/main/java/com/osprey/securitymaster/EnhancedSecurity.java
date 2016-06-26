package com.osprey.securitymaster;

import java.time.ZonedDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class EnhancedSecurity {

	private final SecurityKey key;
	private ZonedDateTime timestamp;

	private double volatility30;
	private double volatility90;
	private double volatility360;
	private double ema4;
	private double ema9;
	private double ema12;
	private double ema15;
	private double ema26;
	private double ema50;
	private double ema200;
	private double beta30;
	private double beta90;
	private double beta360;
	private double alphaForEma;

	public EnhancedSecurity(SecurityKey key) {
		this.key = key;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public double getVolatility30() {
		return volatility30;
	}

	public void setVolatility30(double volatility30) {
		this.volatility30 = volatility30;
	}

	public double getVolatility90() {
		return volatility90;
	}

	public void setVolatility90(double volatility90) {
		this.volatility90 = volatility90;
	}

	public double getVolatility360() {
		return volatility360;
	}

	public void setVolatility360(double volatility360) {
		this.volatility360 = volatility360;
	}

	public double getEma4() {
		return ema4;
	}

	public void setEma4(double ema4) {
		this.ema4 = ema4;
	}

	public double getEma9() {
		return ema9;
	}

	public void setEma9(double ema9) {
		this.ema9 = ema9;
	}

	public double getEma12() {
		return ema12;
	}

	public void setEma12(double ema12) {
		this.ema12 = ema12;
	}

	public double getEma15() {
		return ema15;
	}

	public void setEma15(double ema15) {
		this.ema15 = ema15;
	}

	public double getEma26() {
		return ema26;
	}

	public void setEma26(double ema26) {
		this.ema26 = ema26;
	}

	public double getEma50() {
		return ema50;
	}

	public void setEma50(double ema50) {
		this.ema50 = ema50;
	}

	public double getEma200() {
		return ema200;
	}

	public void setEma200(double ema200) {
		this.ema200 = ema200;
	}

	public double getBeta30() {
		return beta30;
	}

	public void setBeta30(double beta30) {
		this.beta30 = beta30;
	}

	public double getBeta90() {
		return beta90;
	}

	public void setBeta90(double beta90) {
		this.beta90 = beta90;
	}

	public double getBeta360() {
		return beta360;
	}

	public void setBeta360(double beta360) {
		this.beta360 = beta360;
	}

	public double getAlphaForEma() {
		return alphaForEma;
	}

	public void setAlphaForEma(double alphaForEma) {
		this.alphaForEma = alphaForEma;
	}

	public SecurityKey getKey() {
		return key;
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
		EnhancedSecurity other = (EnhancedSecurity) obj;
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
