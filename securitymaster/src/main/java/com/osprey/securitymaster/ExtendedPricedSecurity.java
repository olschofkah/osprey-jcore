package com.osprey.securitymaster;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ExtendedPricedSecurity extends PricedSecurity {

	private double open;
	private double _1DayMin;
	private double _1DayMax;
	private double _52WeekMin;
	private double _52WeekMax;
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

	public double get_1DayMin() {
		return _1DayMin;
	}

	public void set_1DayMin(double _1DayMin) {
		this._1DayMin = _1DayMin;
	}

	public double get_1DayMax() {
		return _1DayMax;
	}

	public void set_1DayMax(double _1DayMax) {
		this._1DayMax = _1DayMax;
	}

	public double get_52WeekMin() {
		return _52WeekMin;
	}

	public void set_52WeekMin(double _52WeekMin) {
		this._52WeekMin = _52WeekMin;
	}

	public double get_52WeekMax() {
		return _52WeekMax;
	}

	public void set_52WeekMax(double _52WeekMax) {
		this._52WeekMax = _52WeekMax;
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

}
