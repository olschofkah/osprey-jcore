package com.osprey.securitymaster;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ExtendedPricedSecurity extends PricedSecurity {

	private double open;
	private double dayHigh;
	private double dayLow;
	private double _52High;
	private double _52Low;
	private double marketCap;
	private double sharesOutstanding;
	private double eps;
	private double peRatio;
	private double annualDividend;
	private double annualYield;
	private double pctHeldByInst;
	private double shortInt;

	private double beta;
	private double historicalVolatility;

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(double marketCap) {
		this.marketCap = marketCap;
	}

	public double getSharesOutstanding() {
		return sharesOutstanding;
	}

	public void setSharesOutstanding(double sharesOutstanding) {
		this.sharesOutstanding = sharesOutstanding;
	}

	public double getEps() {
		return eps;
	}

	public void setEps(double eps) {
		this.eps = eps;
	}

	public double getPeRatio() {
		return peRatio;
	}

	public void setPeRatio(double peRatio) {
		this.peRatio = peRatio;
	}

	public double getAnnualDividend() {
		return annualDividend;
	}

	public void setAnnualDividend(double annualDividend) {
		this.annualDividend = annualDividend;
	}

	public double getAnnualYield() {
		return annualYield;
	}

	public void setAnnualYield(double annualYield) {
		this.annualYield = annualYield;
	}

	public double getPctHeldByInst() {
		return pctHeldByInst;
	}

	public void setPctHeldByInst(double pctHeldByInst) {
		this.pctHeldByInst = pctHeldByInst;
	}

	public double getShortInt() {
		return shortInt;
	}

	public void setShortInt(double shortInt) {
		this.shortInt = shortInt;
	}

	public double getBeta() {
		return beta;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public double getHistoricalVolatility() {
		return historicalVolatility;
	}

	public void setHistoricalVolatility(double historicalVolatility) {
		this.historicalVolatility = historicalVolatility;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	public double getDayHigh() {
		return dayHigh;
	}

	public void setDayHigh(double dayHigh) {
		this.dayHigh = dayHigh;
	}

	public double getDayLow() {
		return dayLow;
	}

	public void setDayLow(double dayLow) {
		this.dayLow = dayLow;
	}

	public double get_52High() {
		return _52High;
	}

	public void set_52High(double _52High) {
		this._52High = _52High;
	}

	public double get_52Low() {
		return _52Low;
	}

	public void set_52Low(double _52Low) {
		this._52Low = _52Low;
	}

}
