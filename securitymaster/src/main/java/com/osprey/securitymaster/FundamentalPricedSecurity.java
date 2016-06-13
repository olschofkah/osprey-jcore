package com.osprey.securitymaster;

import java.time.ZonedDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.osprey.securitymaster.constants.EarningsReportTime;

public class FundamentalPricedSecurity extends PricedSecurity {

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

	private ZonedDateTime nextEarningsDate;
	private ZonedDateTime previousEarningsDate;
	private EarningsReportTime nextEarningsReportTime;
	private EarningsReportTime previousEarningsReportTime;

	private ZonedDateTime nextDivDate;
	private ZonedDateTime previousDivDate;

	public FundamentalPricedSecurity(String ticker) {
		super(ticker);
	}

	public FundamentalPricedSecurity(Security s) {
		this(s.ticker);
		this.instrumentType = s.instrumentType;
		this.timestamp = s.timestamp;
	}

	public FundamentalPricedSecurity(PricedSecurity s) {
		this((Security) s);

		// Set Priced Security vars
		this.close = s.close;
		this.open = s.open;
		this.ask = s.ask;
		this.bid = s.bid;
		this.volume = s.volume;
	}

	/**
	 * Copy Constructor
	 * 
	 * @param s
	 *            FundamentalPricedSecurity to copy
	 */
	public FundamentalPricedSecurity(FundamentalPricedSecurity s) {
		this((PricedSecurity) s);

		this.dayHigh = s.dayHigh;
		this.dayLow = s.dayLow;
		this._52High = s._52High;
		this._52Low = s._52Low;
		this.marketCap = s.marketCap;
		this.sharesOutstanding = s.sharesOutstanding;
		this.eps = s.eps;
		this.peRatio = s.peRatio;
		this.annualDividend = s.annualDividend;
		this.annualYield = s.annualYield;
		this.pctHeldByInst = s.pctHeldByInst;
		this.shortInt = s.shortInt;

		this.beta = s.beta;
		this.historicalVolatility = s.historicalVolatility;

		this.nextEarningsDate = s.nextEarningsDate;
		this.previousEarningsDate = s.previousEarningsDate;
		this.nextEarningsReportTime = s.nextEarningsReportTime;
		this.previousEarningsReportTime = s.previousEarningsReportTime;

		this.nextDivDate = s.nextDivDate;
		this.previousDivDate = s.previousDivDate;
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

}
