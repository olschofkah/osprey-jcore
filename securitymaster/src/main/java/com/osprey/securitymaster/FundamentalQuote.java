package com.osprey.securitymaster;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FundamentalQuote {

	private final SecurityKey key;
	private ZonedDateTime timestamp;
	private final LocalDate date;

	private long _10DayAvgVolume;
	private double _200DayAverage;
	private double _50DayAverage;
	private double _52WeekHigh;
	private double _52WeekLow;
	private long averageVolume;
	private double beta;
	private double bookValue;
	private double currentRatio;
	private double debtToEquity;
	private double floatShares;
	private double earningsAvg;
	private double earningsGrowth;
	private double earningsHigh;
	private double earningsLow;
	private double earningsQtrGrowth;
	private double ebitda;
	private double ebitdaMargins;
	private double enterpriseToEbitda;
	private double enterpriseToRevenue;
	private double enterpriseValue;
	private double forwardEps;
	private double forwardPe;
	private double freeCashflow;
	private double grossMargins;
	private double grossProfits;
	private double heldPctInsiders;
	private double heldPctInstitutions;
	private long marketCap;
	private double netIncomeToCommon;
	private double operatingCashflow;
	private double operatingMargins;
	private double pegRatio;
	private double priceToBook;
	private double priceToSales;
	private double profitMargins;
	private double quickRatio;
	private double returnOnAssets;
	private double returnOnEquity;
	private double revenueAvg;
	private double revenueGrowth;
	private double revenueHigh;
	private double revenueLow;
	private double revenuePerShare;
	private double revenueQtrGrowth;
	private double sharesOutstanding;
	private double sharesShort;
	private double sharesShortPriorMonth;
	private double shortPercentOfFloat;
	private double shortRatio;
	private double totalCash;
	private double totalCashPerShare;
	private double totalDebt;
	private double totalRevenue;
	private double trailingEps;
	private double trailingPe;
	private long totalAssets;
	private double yield;
	private double dividendRate;
	private double dividendYield;

	private long buyInfoShares;
	private long sellInfoShares;
	private double sellPercentInsiderShares;
	private double netPercentInsiderShares;
	private long netInsiderSharesBuying;
	private double netInstBuyingPercent;
	private long totalInsiderShares;
	private double insiderPercentHeld;
	private double institutionsPercentHeld;
	private double institutionsFloatPercentHeld;
	private long institutionsCount;

	// Calculated Values
	private double volatility;
	private double rotationIndicator;
	private double _8DayEma;
	private double _10DayEma;
	private double _15DayEma;
	private double _20DayEma;
	private double _50DayEma;
	private double _100DayEma;
	private double _200DayEma;

	public FundamentalQuote(String symbol, LocalDate dt, ZonedDateTime timestamp) {
		this(new SecurityKey(symbol, null), dt, timestamp);
	}

	public FundamentalQuote(SecurityKey s, LocalDate dt, ZonedDateTime timestamp) {
		this.key = s;
		this.date = dt;
		this.timestamp = timestamp;
	}

	public FundamentalQuote(SecurityKey key, LocalDate date) {
		this(key, date, null);
	}

	public long get_10DayAvgVolume() {
		return _10DayAvgVolume;
	}

	public double get_200DayAverage() {
		return _200DayAverage;
	}

	public double get_50DayAverage() {
		return _50DayAverage;
	}

	public double get_52WeekHigh() {
		return _52WeekHigh;
	}

	public double get_52WeekLow() {
		return _52WeekLow;
	}

	public long getAverageVolume() {
		return averageVolume;
	}

	public double getBeta() {
		return beta;
	}

	public double getBookValue() {
		return bookValue;
	}

	public double getCurrentRatio() {
		return currentRatio;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getDebtToEquity() {
		return debtToEquity;
	}

	public double getFloatShares() {
		return floatShares;
	}

	public double getEarningsAvg() {
		return earningsAvg;
	}

	public double getEarningsGrowth() {
		return earningsGrowth;
	}

	public double getEarningsHigh() {
		return earningsHigh;
	}

	public double getEarningsLow() {
		return earningsLow;
	}

	public double getEarningsQtrGrowth() {
		return earningsQtrGrowth;
	}

	public double getEbitda() {
		return ebitda;
	}

	public double getEbitdaMargins() {
		return ebitdaMargins;
	}

	public double getEnterpriseToEbitda() {
		return enterpriseToEbitda;
	}

	public double getEnterpriseToRevenue() {
		return enterpriseToRevenue;
	}

	public double getEnterpriseValue() {
		return enterpriseValue;
	}

	public double getForwardEps() {
		return forwardEps;
	}

	public double getForwardPe() {
		return forwardPe;
	}

	public double getFreeCashflow() {
		return freeCashflow;
	}

	public double getGrossMargins() {
		return grossMargins;
	}

	public double getGrossProfits() {
		return grossProfits;
	}

	public double getHeldPctInsiders() {
		return heldPctInsiders;
	}

	public double getHeldPctInstitutions() {
		return heldPctInstitutions;
	}

	public SecurityKey getKey() {
		return key;
	}

	public long getMarketCap() {
		return marketCap;
	}

	public double getNetIncomeToCommon() {
		return netIncomeToCommon;
	}

	public double getOperatingCashflow() {
		return operatingCashflow;
	}

	public double getOperatingMargins() {
		return operatingMargins;
	}

	public double getPegRatio() {
		return pegRatio;
	}

	public double getPriceToBook() {
		return priceToBook;
	}

	public double getPriceToSales() {
		return priceToSales;
	}

	public double getProfitMargins() {
		return profitMargins;
	}

	public double getQuickRatio() {
		return quickRatio;
	}

	public double getReturnOnAssets() {
		return returnOnAssets;
	}

	public double getReturnOnEquity() {
		return returnOnEquity;
	}

	public double getRevenueAvg() {
		return revenueAvg;
	}

	public double getRevenueGrowth() {
		return revenueGrowth;
	}

	public double getRevenueHigh() {
		return revenueHigh;
	}

	public double getRevenueLow() {
		return revenueLow;
	}

	public double getRevenuePerShare() {
		return revenuePerShare;
	}

	public double getSharesOutstanding() {
		return sharesOutstanding;
	}

	public double getSharesShort() {
		return sharesShort;
	}

	public double getSharesShortPriorMonth() {
		return sharesShortPriorMonth;
	}

	public double getShortPercentOfFloat() {
		return shortPercentOfFloat;
	}

	public double getShortRatio() {
		return shortRatio;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public double getTotalCash() {
		return totalCash;
	}

	public double getTotalCashPerShare() {
		return totalCashPerShare;
	}

	public double getTotalDebt() {
		return totalDebt;
	}

	public double getTotalRevenue() {
		return totalRevenue;
	}

	public double getTrailingEps() {
		return trailingEps;
	}

	public double getTrailingPe() {
		return trailingPe;
	}

	public void set_10DayAvgVolume(long _10DayAvgVolume) {
		this._10DayAvgVolume = _10DayAvgVolume;
	}

	public void set_200DayAverage(double _200DayAverage) {
		this._200DayAverage = _200DayAverage;
	}

	public void set_50DayAverage(double _50DayAverage) {
		this._50DayAverage = _50DayAverage;
	}

	public void set_52WeekHigh(double _52WeekHigh) {
		this._52WeekHigh = _52WeekHigh;
	}

	public void set_52WeekLow(double _52WeekLow) {
		this._52WeekLow = _52WeekLow;
	}

	public void setAverageVolume(long averageVolume) {
		this.averageVolume = averageVolume;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public void setBookValue(double bookValue) {
		this.bookValue = bookValue;
	}

	public void setCurrentRatio(double currentRatio) {
		this.currentRatio = currentRatio;
	}

	public void setDebtToEquity(double debtToEquity) {
		this.debtToEquity = debtToEquity;
	}

	public void setFloatShares(double floatShares) {
		this.floatShares = floatShares;
	}

	public void setEarningsAvg(double earningsAvg) {
		this.earningsAvg = earningsAvg;
	}

	public void setEarningsGrowth(double earningsGrowth) {
		this.earningsGrowth = earningsGrowth;
	}

	public void setEarningsHigh(double earningsHigh) {
		this.earningsHigh = earningsHigh;
	}

	public void setEarningsLow(double earningsLow) {
		this.earningsLow = earningsLow;
	}

	public void setEarningsQtrGrowth(double earningsQtrGrowth) {
		this.earningsQtrGrowth = earningsQtrGrowth;
	}

	public void setEbitda(double ebitda) {
		this.ebitda = ebitda;
	}

	public void setEbitdaMargins(double ebitdaMargins) {
		this.ebitdaMargins = ebitdaMargins;
	}

	public void setEnterpriseToEbitda(double enterpriseToEbitda) {
		this.enterpriseToEbitda = enterpriseToEbitda;
	}

	public void setEnterpriseToRevenue(double enterpriseToRevenue) {
		this.enterpriseToRevenue = enterpriseToRevenue;
	}

	public void setEnterpriseValue(double enterpriseValue) {
		this.enterpriseValue = enterpriseValue;
	}

	public void setForwardEps(double forwardEps) {
		this.forwardEps = forwardEps;
	}

	public void setForwardPe(double forwardPe) {
		this.forwardPe = forwardPe;
	}

	public void setFreeCashflow(double freeCashflow) {
		this.freeCashflow = freeCashflow;
	}

	public void setGrossMargins(double grossMargins) {
		this.grossMargins = grossMargins;
	}

	public void setGrossProfits(double grossProfits) {
		this.grossProfits = grossProfits;
	}

	public void setHeldPctInsiders(double heldPctInsiders) {
		this.heldPctInsiders = heldPctInsiders;
	}

	public void setHeldPctInstitutions(double heldPctInstitutions) {
		this.heldPctInstitutions = heldPctInstitutions;
	}

	public void setMarketCap(long marketCap) {
		this.marketCap = marketCap;
	}

	public void setNetIncomeToCommon(double netIncomeToCommon) {
		this.netIncomeToCommon = netIncomeToCommon;
	}

	public void setOperatingCashflow(double operatingCashflow) {
		this.operatingCashflow = operatingCashflow;
	}

	public void setOperatingMargins(double operatingMargins) {
		this.operatingMargins = operatingMargins;
	}

	public void setPegRatio(double pegRatio) {
		this.pegRatio = pegRatio;
	}

	public void setPriceToBook(double priceToBook) {
		this.priceToBook = priceToBook;
	}

	public void setPriceToSales(double priceToSales) {
		this.priceToSales = priceToSales;
	}

	public void setProfitMargins(double profitMargins) {
		this.profitMargins = profitMargins;
	}

	public void setQuickRatio(double quickRatio) {
		this.quickRatio = quickRatio;
	}

	public void setReturnOnAssets(double returnOnAssets) {
		this.returnOnAssets = returnOnAssets;
	}

	public void setReturnOnEquity(double returnOnEquity) {
		this.returnOnEquity = returnOnEquity;
	}

	public void setRevenueAvg(double revenueAvg) {
		this.revenueAvg = revenueAvg;
	}

	public void setRevenueGrowth(double revenueGrowth) {
		this.revenueGrowth = revenueGrowth;
	}

	public void setRevenueHigh(double revenueHigh) {
		this.revenueHigh = revenueHigh;
	}

	public void setRevenueLow(double revenueLow) {
		this.revenueLow = revenueLow;
	}

	public void setRevenuePerShare(double revenuePerShare) {
		this.revenuePerShare = revenuePerShare;
	}

	public void setSharesOutstanding(double sharesOutstanding) {
		this.sharesOutstanding = sharesOutstanding;
	}

	public void setSharesShort(double sharesShort) {
		this.sharesShort = sharesShort;
	}

	public void setSharesShortPriorMonth(double sharesShortPriorMonth) {
		this.sharesShortPriorMonth = sharesShortPriorMonth;
	}

	public void setShortPercentOfFloat(double shortPercentOfDouble) {
		this.shortPercentOfFloat = shortPercentOfDouble;
	}

	public void setShortRatio(double shortRatio) {
		this.shortRatio = shortRatio;
	}

	public void setTotalCash(double totalCash) {
		this.totalCash = totalCash;
	}

	public void setTotalCashPerShare(double totalCashPerShare) {
		this.totalCashPerShare = totalCashPerShare;
	}

	public void setTotalDebt(double totalDebt) {
		this.totalDebt = totalDebt;
	}

	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public void setTrailingEps(double trailingEps) {
		this.trailingEps = trailingEps;
	}

	public void setTrailingPe(double trailingPe) {
		this.trailingPe = trailingPe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((getTimestamp() == null) ? 0 : getTimestamp().hashCode());
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
		FundamentalQuote other = (FundamentalQuote) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (getTimestamp() == null) {
			if (other.getTimestamp() != null)
				return false;
		} else if (!getTimestamp().equals(other.getTimestamp()))
			return false;
		return true;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public void setTotalAssets(Long raw) {
		this.totalAssets = raw;
	}

	public long getTotalAssets() {
		return this.totalAssets;
	}

	public double getYield() {
		return yield;
	}

	public void setYield(double yield) {
		this.yield = yield;
	}

	public double getDividendRate() {
		return dividendRate;
	}

	public void setDividendRate(double dividendRate) {
		this.dividendRate = dividendRate;
	}

	public double getDividendYield() {
		return dividendYield;
	}

	public void setDividendYield(double dividendYield) {
		this.dividendYield = dividendYield;
	}

	public double getRevenueQtrGrowth() {
		return revenueQtrGrowth;
	}

	public void setRevenueQtrGrowth(double revenueQtrGrowth) {
		this.revenueQtrGrowth = revenueQtrGrowth;
	}

	public double getVolatility() {
		return volatility;
	}

	public void setVolatility(double volatility) {
		this.volatility = volatility;
	}

	public double get_200DayEma() {
		return _200DayEma;
	}

	public void set_200DayEma(double _200DayEma) {
		this._200DayEma = _200DayEma;
	}

	public double get_10DayEma() {
		return _10DayEma;
	}

	public void set_10DayEma(double _10DayEma) {
		this._10DayEma = _10DayEma;
	}

	public double get_20DayEma() {
		return _20DayEma;
	}

	public void set_20DayEma(double _20DayEma) {
		this._20DayEma = _20DayEma;
	}

	public double get_50DayEma() {
		return _50DayEma;
	}

	public void set_50DayEma(double _50DayEma) {
		this._50DayEma = _50DayEma;
	}

	public double get_100DayEma() {
		return _100DayEma;
	}

	public void set_100DayEma(double _100DayEma) {
		this._100DayEma = _100DayEma;
	}

	public double get_8DayEma() {
		return _8DayEma;
	}

	public void set_8DayEma(double _8DayEma) {
		this._8DayEma = _8DayEma;
	}

	public double get_15DayEma() {
		return _15DayEma;
	}

	public void set_15DayEma(double _15DayEma) {
		this._15DayEma = _15DayEma;
	}

	public double getRotationIndicator() {
		return rotationIndicator;
	}

	public void setRotationIndicator(double rotationIndicator) {
		this.rotationIndicator = rotationIndicator;
	}

	public void setBuyInfoShares(Long raw) {
		this.buyInfoShares = raw;
	}

	public void setSellInfoShares(Long raw) {
		this.sellInfoShares = raw;
	}

	public void setSellPercentInsiderShares(Double raw) {
		this.sellPercentInsiderShares = raw;
	}

	public void setNetPercentInsiderShares(Double raw) {
		this.netPercentInsiderShares = raw;
	}

	public void setNetInstSharesBuying(Long raw) {
		this.netInsiderSharesBuying = raw;
	}

	public void setNetInstBuyingPercent(Double raw) {
		this.netInstBuyingPercent = raw;
	}

	public void setTotalInsiderShares(Long raw) {
		this.totalInsiderShares = raw;
	}

	public void setInsidersPercentHeld(Double raw) {
		this.insiderPercentHeld = raw;
	}

	public void setInstitutionsPercentHeld(Double raw) {
		this.institutionsPercentHeld = raw;
	}

	public void setInstitutionsFloatPercentHeld(Double raw) {
		this.institutionsFloatPercentHeld = raw;
	}

	public void setInstitutionsCount(Long raw) {
		this.institutionsCount = raw;
	}

	public long getBuyInfoShares() {
		return buyInfoShares;
	}

	public long getSellInfoShares() {
		return sellInfoShares;
	}

	public double getSellPercentInsiderShares() {
		return sellPercentInsiderShares;
	}

	public double getNetPercentInsiderShares() {
		return netPercentInsiderShares;
	}

	public long getNetInsiderSharesBuying() {
		return netInsiderSharesBuying;
	}

	public double getNetInstBuyingPercent() {
		return netInstBuyingPercent;
	}

	public long getTotalInsiderShares() {
		return totalInsiderShares;
	}

	public double getInsiderPercentHeld() {
		return insiderPercentHeld;
	}

	public double getInstitutionsPercentHeld() {
		return institutionsPercentHeld;
	}

	public double getInstitutionsFloatPercentHeld() {
		return institutionsFloatPercentHeld;
	}

	public long getInstitutionsCount() {
		return institutionsCount;
	}

}
