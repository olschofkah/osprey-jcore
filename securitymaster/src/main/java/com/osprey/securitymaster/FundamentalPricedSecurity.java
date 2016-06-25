package com.osprey.securitymaster;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FundamentalPricedSecurity extends PricedSecurity {

	protected double dayHigh;
	protected double dayLow;
	protected double _52High;
	protected double _52Low;
	protected double previousClose;
	protected double marketCap;
	protected long sharesOutstanding;
	protected double eps;
	protected double peRatio;
	protected double annualDividend;
	protected double annualYield;
	protected double pctHeldByInst;
	protected double shortInt;

	protected double debtToEquity;
	protected double returnOnAssets;
	protected double returnOnEquity;
	protected double revenuePerShare;
	protected double revenueGrowth;

	protected double beta;
	protected double historicalVolatility;

	protected LocalDate nextEarningsDateLower;
	protected LocalDate nextEarningsDateUpper;

	protected LocalDate nextDivDate;
	protected LocalDate nextExDivDate;

	public FundamentalPricedSecurity(String ticker) {
		super(ticker);
	}

	public FundamentalPricedSecurity(Security s) {
		super(s);
	}

	public FundamentalPricedSecurity(PricedSecurity s) {
		super(s);
	}

	/**
	 * Copy Constructor
	 * 
	 * @param s
	 *            FundamentalPricedSecurity to copy
	 */
	public FundamentalPricedSecurity(FundamentalPricedSecurity s) {
		super((PricedSecurity) s);

		this.dayHigh = s.dayHigh;
		this.dayLow = s.dayLow;
		this._52High = s._52High;
		this._52Low = s._52Low;
		this.previousClose = s.previousClose;
		this.marketCap = s.marketCap;
		this.sharesOutstanding = s.sharesOutstanding;
		this.eps = s.eps;
		this.peRatio = s.peRatio;
		this.annualDividend = s.annualDividend;
		this.annualYield = s.annualYield;
		this.pctHeldByInst = s.pctHeldByInst;
		this.shortInt = s.shortInt;

		this.debtToEquity = s.debtToEquity;
		this.returnOnAssets = s.returnOnAssets;
		this.returnOnEquity = s.returnOnEquity;
		this.revenuePerShare = s.revenuePerShare;
		this.revenueGrowth = s.revenueGrowth;

		this.beta = s.beta;
		this.historicalVolatility = s.historicalVolatility;

		this.nextEarningsDateLower = s.nextEarningsDateLower;
		this.nextEarningsDateUpper = s.nextEarningsDateUpper;
		this.nextDivDate = s.nextDivDate;
		this.nextExDivDate = s.nextExDivDate;
	}

	public LocalDate getNextEarningsDate() {
		return nextEarningsDateLower;
	}

	public void setNextEarningsDateLower(LocalDate nextEarningsDate) {
		this.nextEarningsDateLower = nextEarningsDate;
	}

	public LocalDate getNextDivDate() {
		return nextDivDate;
	}

	public void setNextDivDate(LocalDate nextDivDate) {
		this.nextDivDate = nextDivDate;
	}

	public double getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(double marketCap) {
		this.marketCap = marketCap;
	}

	public long getSharesOutstanding() {
		return sharesOutstanding;
	}

	public void setSharesOutstanding(long sharesOutstanding) {
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

	public LocalDate getNextEarningsDateUpper() {
		return nextEarningsDateUpper;
	}

	public void setNextEarningsDateUpper(LocalDate nextEarningsDateUpper) {
		this.nextEarningsDateUpper = nextEarningsDateUpper;
	}

	public LocalDate getNextExDivDate() {
		return nextExDivDate;
	}

	public void setNextExDivDate(LocalDate nextExDivDate) {
		this.nextExDivDate = nextExDivDate;
	}

	public double getPreviousClose() {
		return previousClose;
	}

	public void setPreviousClose(double previousClose) {
		this.previousClose = previousClose;
	}

	public double getDebtToEquity() {
		return debtToEquity;
	}

	public void setDebtToEquity(double debtToEquity) {
		this.debtToEquity = debtToEquity;
	}

	public double getReturnOnAssets() {
		return returnOnAssets;
	}

	public void setReturnOnAssets(double returnOnAssets) {
		this.returnOnAssets = returnOnAssets;
	}

	public double getReturnOnEquity() {
		return returnOnEquity;
	}

	public void setReturnOnEquity(double returnOnEquity) {
		this.returnOnEquity = returnOnEquity;
	}

	public double getRevenuePerShare() {
		return revenuePerShare;
	}

	public void setRevenuePerShare(double revenuePerShare) {
		this.revenuePerShare = revenuePerShare;
	}

	public double getRevenueGrowth() {
		return revenueGrowth;
	}

	public void setRevenueGrowth(double revenueGrowth) {
		this.revenueGrowth = revenueGrowth;
	}

}
