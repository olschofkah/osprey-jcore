package com.osprey.math;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.util.Pair;

import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.math.exception.InvalidPeriodException;
import com.osprey.math.exception.MathException;
import com.osprey.math.result.SMAPair;
import com.osprey.math.result.StochasticOscillatorCurve;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OptionType;
import com.osprey.securitymaster.constants.OspreyConstants;
import com.osprey.securitymaster.secondary.HistoricalGainQuote;
import com.osprey.securitymaster.secondary.HistoricalLossQuote;

public final class OspreyQuantMath {

	private static final int MOVING_AVERAGE_MAGIC_NUMBER = OspreyConstants.MARKET_DAYS_IN_YEAR / 2;

	public static double ema(int p, int historicalOffset, List<HistoricalQuote> prices) {
		double alpha = 2.0 / (p + 1.0);
		return smoothedMovingAverage(p, historicalOffset, alpha, prices);
	}

	public static double wildersMovingAverage(int p, int historicalOffset, List<HistoricalQuote> prices) {
		double alpha = 1.0 / p;
		return smoothedMovingAverage(p, historicalOffset, alpha, prices);
	}

	/**
	 * EMA(t) = EMA(t-1) + smoothing factor * (Price(t) - EMA(t-1)
	 * 
	 * @param seedAverage
	 * @param p
	 * @param alpha
	 *            - scale from 0 to 1
	 * @param offset
	 *            calculate the p ema for n days ago.
	 * @param prices
	 * @return
	 */
	private static double smoothedMovingAverage(int p, int offset, double alpha, List<HistoricalQuote> prices) {

		if (p < 2) {
			throw new InvalidPeriodException();
		}

		double seedAverage = sma(p, offset + p + MOVING_AVERAGE_MAGIC_NUMBER - 1, prices);

		if (p + offset + MOVING_AVERAGE_MAGIC_NUMBER > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double ma = seedAverage;

		for (int i = p + offset + MOVING_AVERAGE_MAGIC_NUMBER; i >= offset; --i) {
			ma = (prices.get(i).getClose() - ma) * alpha + ma;
		}

		return ma;
	}

	public static Map<String, List<Double>> macdCurves(int p0, int p1, int pS, List<HistoricalQuote> prices) {

		// TODO switch to a custom object returned

		final double a0 = 2.0 / (p0 + 1.0);
		final double a1 = 2.0 / (p1 + 1.0);
		final double aS = 2.0 / (pS + 1.0);

		if (p0 < 2 || pS < 2 || p0 > p1) {
			throw new InvalidPeriodException();
		}

		// This will check that we have enough history to calculate MACD.
		int mamnPlusP1 = p1 + MOVING_AVERAGE_MAGIC_NUMBER;
		double seedAverage = sma(p1, mamnPlusP1 - 1, prices);

		double ma0 = seedAverage;
		double ma1 = seedAverage;

		List<Double> macdCurve = new ArrayList<>(mamnPlusP1);

		HistoricalQuote hq;
		for (int i = mamnPlusP1; i >= 0; --i) {
			hq = prices.get(i);
			ma0 = (hq.getClose() - ma0) * a0 + ma0;
			ma1 = (hq.getClose() - ma1) * a1 + ma1;
			macdCurve.add(ma0 - ma1);
		}

		double macdSignal = macdCurve.get(0);
		List<Double> macdSignalCurve = new ArrayList<>(mamnPlusP1);

		for (int i = 1; i < macdCurve.size(); ++i) {
			macdSignal = (macdCurve.get(i) - macdSignal) * aS + macdSignal;
			macdSignalCurve.add(macdSignal);
		}

		Collections.reverse(macdCurve);
		Collections.reverse(macdSignalCurve);

		Map<String, List<Double>> curves = new HashMap<>();

		curves.put("macd", macdCurve);
		curves.put("macdSignal", macdSignalCurve);

		return curves;
	}

	public static Map<String, List<Pair<LocalDate, Double>>> macdCurvesWithDates(int p0, int p1, int pS,
			List<HistoricalQuote> prices) {

		// TODO switch to a custom object returned

		final double a0 = 2.0 / (p0 + 1.0);
		final double a1 = 2.0 / (p1 + 1.0);
		final double aS = 2.0 / (pS + 1.0);

		if (p0 < 2 || pS < 2 || p0 > p1) {
			throw new InvalidPeriodException();
		}

		// This will check that we have enough history to calculate MACD.
		int mamnPlusP1 = p1 + MOVING_AVERAGE_MAGIC_NUMBER;
		double seedAverage = sma(p1, mamnPlusP1 - 1, prices);

		double ma0 = seedAverage;
		double ma1 = seedAverage;
		double macd = 0;

		List<Pair<LocalDate, Double>> macdCurve = new ArrayList<>(mamnPlusP1);

		HistoricalQuote hq;
		for (int i = mamnPlusP1; i >= 0; --i) {
			hq = prices.get(i);
			ma0 = (hq.getClose() - ma0) * a0 + ma0;
			ma1 = (hq.getClose() - ma1) * a1 + ma1;
			macd = ma0 - ma1;
			macdCurve.add(new Pair<>(hq.getHistoricalDate(), macd));
		}

		double macdSignal = macdCurve.get(0).getValue();
		List<Pair<LocalDate, Double>> macdSignalCurve = new ArrayList<>(mamnPlusP1);

		for (int i = 1; i < macdCurve.size(); ++i) {
			macdSignal = (macdCurve.get(i).getValue() - macdSignal) * aS + macdSignal;
			macdSignalCurve.add(new Pair<>(macdCurve.get(i).getKey(), macdSignal));
		}

		Collections.reverse(macdCurve);
		Collections.reverse(macdSignalCurve);

		Map<String, List<Pair<LocalDate, Double>>> curves = new HashMap<>();

		curves.put("macd", macdCurve);
		curves.put("macdSignal", macdSignalCurve);

		return curves;
	}

	/**
	 * 
	 * https://en.wikipedia.org/wiki/Relative_strength_index
	 * 
	 * Use close price for now instead of adj close
	 * 
	 * @param p
	 * @param offset
	 * @param prices
	 * @return
	 */
	public static double rsiUsingSma(int p, int offset, List<HistoricalQuote> prices) {

		if (p < 2) {
			throw new InvalidPeriodException();
		}

		if (p + offset > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double aveGain = 0.0;
		double aveLoss = 0.0;
		for (int i = offset; i < p + offset; ++i) {
			double change = prices.get(i).getClose() - prices.get(i + 1).getClose();
			if (change >= 0) {
				aveGain += change;
			} else {
				aveLoss += change * -1;
			}
		}

		double rs = (aveGain) / (aveLoss);
		return 100.0 - 100.0 / (1.0 + rs);

	}

	public static double stochasticOscillatorSlowK(int periodK, int lookBackPeriod, int offset,
			List<HistoricalQuote> prices) {
		return stochasticOscillatorFastK(periodK, lookBackPeriod, offset, prices) / periodK;
	}

	private static double stochasticOscillatorFastK(int periodK, int lookBackPeriod, int offset,
			List<HistoricalQuote> prices) {

		if (periodK == 0) {
			return 0;
		}

		if (lookBackPeriod < 2) {
			throw new InvalidPeriodException();
		}

		if (lookBackPeriod + offset > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double maxHigh = 0;
		double minLow = 10000000;

		HistoricalQuote hq;
		for (int i = offset; i < lookBackPeriod + offset; ++i) {
			hq = prices.get(i);
			if (maxHigh < hq.getHigh()) {
				maxHigh = hq.getHigh();
			}
			if (minLow > hq.getLow()) {
				minLow = hq.getLow();
			}
		}

		double fastPK = ((prices.get(offset).getClose() - minLow) / (maxHigh - minLow) * 100.0);
		return fastPK + stochasticOscillatorFastK(periodK - 1, lookBackPeriod, offset + 1, prices);
	}

	public static double stochasticOscillatorSlowD(int periodD, int periodK, int lookBackPeriod, double initialPctK,
			int offset, List<HistoricalQuote> prices) {

		if (periodD < 2) {
			throw new InvalidPeriodException();
		}

		if (periodD + offset > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double pctK = initialPctK;
		int cnt = initialPctK == 0.0 ? 0 : 1;

		// SMA of previous days
		for (; cnt < periodD; ++cnt) {
			pctK += stochasticOscillatorSlowK(periodK, lookBackPeriod, offset + cnt, prices);
		}

		if (cnt == 0) {
			return 0;
		} else {
			return pctK / cnt;
		}
	}

	public static StochasticOscillatorCurve stochasticOscillatorSmaCurves(int lookBackPeriod, int periodK, int periodD,
			int length, List<HistoricalQuote> prices) {

		if (periodK > prices.size() || periodD > prices.size()) {
			throw new InsufficientHistoryException();
		}

		int maxPeriod = periodK > periodD ? periodK : periodD;
		int adjLength = length > prices.size() - maxPeriod ? prices.size() - maxPeriod : length;

		double[] cK = new double[adjLength];
		double[] cD = new double[adjLength];
		LocalDate[] cDate = new LocalDate[adjLength];

		double tmpPctK;
		double tmpPctD;
		for (int i = 0; i < adjLength; i++) {
			tmpPctK = stochasticOscillatorSlowK(periodK, lookBackPeriod, i, prices);
			tmpPctD = stochasticOscillatorSlowD(periodD, periodK, lookBackPeriod, tmpPctK, i, prices);

			cK[i] = tmpPctK;
			cD[i] = tmpPctD;
			cDate[i] = prices.get(i).getHistoricalDate();
		}

		return new StochasticOscillatorCurve(cK, cD, cDate);
	}

	/**
	 * 
	 * https://en.wikipedia.org/wiki/Relative_strength_index
	 * 
	 * Use close price for now instead of adj close
	 * 
	 * @param p
	 * @param offset
	 * @param prices
	 * @return
	 */
	public static double rsiUsingEma(int p, int offset, List<HistoricalQuote> prices) {

		if (p < 2) {
			throw new InvalidPeriodException();
		}

		if (p + offset + MOVING_AVERAGE_MAGIC_NUMBER > prices.size()) {
			throw new InsufficientHistoryException();
		}

		List<HistoricalQuote> upPeriods = new ArrayList<>();
		List<HistoricalQuote> downPeriods = new ArrayList<>();
		for (int i = 0; i < prices.size() - 1; ++i) {
			upPeriods.add(new HistoricalGainQuote(prices.get(i), prices.get(i + 1)));
			downPeriods.add(new HistoricalLossQuote(prices.get(i), prices.get(i + 1)));
		}

		double rs = ema(p, offset, upPeriods) / ema(p, offset, downPeriods);
		return 100.0 - 100.0 / (1.0 + rs);

	}

	/**
	 * 
	 * https://en.wikipedia.org/wiki/Relative_strength_index
	 * 
	 * Use close price for now instead of adj close
	 * 
	 * @param p
	 * @param offset
	 * @param prices
	 * @return
	 */
	public static double rsiUsingWilders(int p, int offset, List<HistoricalQuote> prices) {

		if (p < 2) {
			throw new InvalidPeriodException();
		}

		if (p + offset + MOVING_AVERAGE_MAGIC_NUMBER > prices.size()) {
			throw new InsufficientHistoryException();
		}

		// Build a list of gains and losses for each day and use that to
		// calculate a moving average.
		List<HistoricalQuote> upPeriods = new ArrayList<>();
		List<HistoricalQuote> downPeriods = new ArrayList<>();
		for (int i = 0; i < prices.size() - 1; ++i) {
			upPeriods.add(new HistoricalGainQuote(prices.get(i), prices.get(i + 1)));
			downPeriods.add(new HistoricalLossQuote(prices.get(i), prices.get(i + 1)));
		}

		double rs = wildersMovingAverage(p, offset, upPeriods) / wildersMovingAverage(p, offset, downPeriods);
		return 100.0 - 100.0 / (1.0 + rs);

	}

	public static double stdev(int p, List<HistoricalQuote> prices) {
		if (p < 2) {
			throw new InvalidPeriodException();
		}

		if (p > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double[] closes = new double[prices.size()];
		for (int i = 0; i < prices.size(); ++i) {
			closes[i] = prices.get(i).getClose();
		}

		StandardDeviation sd = new StandardDeviation();
		return sd.evaluate(closes);
	}

	// http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:bollinger_bands
	public static double[] bollingerBands(int p, int offset, List<HistoricalQuote> prices) {

		// TODO @jiayang: do you always calc the mid band w/ sma?

		double midband = sma(p, offset, prices);

		double upperband = midband + stdev(p, prices) * 2.0;

		double lowerband = midband - stdev(p, prices) * 2.0;

		return (new double[] { upperband, midband, lowerband });

	}

	/**
	 * Calculate two Simple Moving Averages simultaneously over a single series
	 * of closing prices.
	 * 
	 * @param p1
	 *            - Period to calculate the average over for result 1
	 * @param p2
	 *            - Period to calculate the average over for result 2
	 * @param prices
	 *            - Prices to use for calculation
	 * @return ( c0 + c1 + c2 + ... + cp) / p for every p
	 */
	public static SMAPair smaPair(int p1, int p2, int offset, List<HistoricalQuote> prices, SecurityQuote quote) {

		if (p1 < 0 || p2 < 0) {
			throw new InvalidPeriodException();
		}

		int r = p1 > p2 ? p1 : p2;

		if (r > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double sma1 = 0;
		double sma2 = 0;
		int p1o = p1 + offset;
		int p2o = p2 + offset;

		double histPrice = 0;
		for (int i = 0 + offset; i < r + offset; ++i) {
			histPrice = i == 0 ? quote.getLast() : prices.get(i).getAdjClose();
			if (i < p1o) {
				sma1 += histPrice;
			}
			if (i < p2o) {
				sma2 += histPrice;
			}
		}

		sma1 /= p1;
		sma2 /= p2;

		return new SMAPair(prices.get(0).getKey().getSymbol(), p1, sma1, p2, sma2);
	}

	/**
	 * Simple Moving Average
	 * 
	 * @param p
	 *            - Period to calculate the average over
	 * @param prices
	 *            - Prices to use for calculation
	 * @return ( c0 + c1 + c2 + ... + cp) / p
	 */
	public static double sma(int p, int offset, List<HistoricalQuote> prices) {

		if (p < 0) {
			throw new InvalidPeriodException();
		}

		if (p + offset > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double sma1 = 0;

		for (int i = offset; i < p + offset; ++i) {
			sma1 += prices.get(i).getClose();
		}

		sma1 /= p;
		return sma1;
	}

	/**
	 * Annual Volatility Annual Volatility is defined as standard deviation
	 * times sqrt(252) standard deviation = sqrt(sum(daily return (i) - average
	 * daily return)^2/n)
	 * 
	 * @param period
	 * @param prices
	 * @return volatility = sqrt(sum(daily return (i) - average daily
	 *         return)^2/n)
	 */
	public static double volatility(int period, List<HistoricalQuote> prices) {

		if (period < 3) {
			throw new InvalidPeriodException();
		}

		if (period > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double dailyReturn;
		double price;
		double previousPrice = prices.get(0).getAdjClose();
		double averageDailyReturn = 0;

		List<Double> dailyReturns = new ArrayList<>(period);

		for (int i = 1; i < period; ++i) {
			price = prices.get(i).getAdjClose();

			dailyReturn = price / previousPrice - 1;
			dailyReturns.add(dailyReturn);

			averageDailyReturn += dailyReturn;

			previousPrice = price;
		}

		averageDailyReturn /= (period - 1);

		double volatility = 0;
		for (double dr : dailyReturns) {
			volatility += Math.pow(dr - averageDailyReturn, 2);
		}

		return Math.pow(volatility / (period - 2), 0.5) * Math.pow(period, 0.5);
	}

	public static double standardNormalDistribution(double x) {
		double top = Math.exp(-0.5 * Math.pow(x, 2));
		double bottom = Math.sqrt(2 * Math.PI);
		return top / bottom;
	}

	// The Black and Scholes (1973) Stock option formula

	/**
	 * @param CallPutFlag
	 *            - char c for call, otherwise put
	 * @param S
	 *            - double stock price
	 * @param X
	 *            - double strike price
	 * @param T
	 *            - double time to maturity in years (1/3 for 4 months)
	 * @param r
	 *            - risk free interest rate
	 * @param v
	 *            - volatility
	 * @return option price for call or put
	 */
	public static double blackScholes(OptionType optionType, double S, double X, double T, double r, double v) {
		double d1, d2;

		d1 = (Math.log(S / X) + (r + v * v / 2) * T) / (v * Math.sqrt(T));
		d2 = d1 - v * Math.sqrt(T);

		if (optionType == OptionType.CALL) {
			return S * cnd(d1) - X * Math.exp(-r * T) * cnd(d2);
		} else {
			return X * Math.exp(-r * T) * cnd(-d2) - S * cnd(-d1);
		}
	}

	private static final double CND_A_1 = 0.31938153;
	private static final double CND_A_2 = -0.356563782;
	private static final double CND_A_3 = 1.781477937;
	private static final double CND_A_4 = -1.821255978;
	private static final double CND_A_5 = 1.330274429;

	// The cumulative normal distribution function
	public static double cnd(double X) {
		double L, K, w;

		L = Math.abs(X);
		K = 1.0 / (1.0 + 0.2316419 * L);
		w = 1.0 - 1.0 / Math.sqrt(2.0 * Math.PI) * Math.exp(-L * L / 2) * (CND_A_1 * K + CND_A_2 * K * K
				+ CND_A_3 * Math.pow(K, 3) + CND_A_4 * Math.pow(K, 4) + CND_A_5 * Math.pow(K, 5));

		if (X < 0.0) {
			w = 1.0 - w;
		}
		return w;
	}

	/**
	 * this is using iterative approach (eg.bisection method) to find the IV, t
	 * 
	 * @param X
	 *            - strike
	 * @param S
	 *            - spot
	 * @param T
	 *            - time to maturity
	 * @param callOptionPrice
	 *            - call option price (current at the money)
	 * @param r
	 *            - interest rate
	 * @return IV
	 */
	public static double impliedVolatility(OptionType option, double X, double S, double T, double callOptionPrice,
			double r) {
		double cpTest = 0;
		double v = 500.0;

		double upper = v;
		double lower = 0;
		double range = Math.abs(lower - upper);

		while (true) {

			cpTest = blackScholes(option, S, X, T, r, v);

			if (cpTest > callOptionPrice) {
				// Implied Volatility - IV has to go down
				upper = v;
				v = (lower + upper) / 2;
			} else {
				// Implied Volatility - IV has to go up
				lower = v;
				v = (lower + upper) / 2;
			}
			range = Math.abs(lower - upper);
			if (range < 0.001)
				break;
		}
		return v;
	}

	/*
	 * http://stockcharts.com/school/doku.php?id=chart_school:
	 * technical_indicators:money_flow_index_mfi 1. Typical Price = (High + Low
	 * + Close)/3 2. Raw Money Flow = Typical Price x Volume 3. Money Flow Ratio
	 * = (14-period Positive Money Flow)/(14-period Negative Money Flow) 4.
	 * Money Flow Index = 100 - 100/(1 + Money Flow Ratio)
	 * 
	 * 
	 */
	public static double moneyFlowIndex(int p, int offset, List<HistoricalQuote> prices) {

		if (p < 2) {
			throw new InvalidPeriodException();
		}

		if (p + offset > prices.size()) {
			throw new InsufficientHistoryException();
		}

		// TODO clean this up.

		double aveGain = 0.0;
		double aveLoss = 0.0;
		for (int i = offset; i < p + offset; ++i) {
			double changeTypicalPrice = 1 / 3
					* (prices.get(i + 1).getClose() + prices.get(i + 1).getHigh() + prices.get(i + 1).getLow())
					- 1 / 3 * (prices.get(i).getClose() + prices.get(i).getHigh() + prices.get(i).getLow());
			double changeRawMoneyFlow = 1 / 3 * prices.get(i + 1).getVolume()
					* (prices.get(i + 1).getClose() + prices.get(i + 1).getHigh() + prices.get(i + 1).getLow())
					- 1 / 3 * prices.get(i).getVolume()
							* (prices.get(i).getClose() + prices.get(i).getHigh() + prices.get(i).getLow());

			if (changeTypicalPrice >= 0) {
				aveGain += changeRawMoneyFlow;
			} else {
				aveLoss += changeRawMoneyFlow * -1;
			}
		}

		double moneyFlowRatio = (aveGain) / (aveLoss);
		return 100.0 - 100.0 / (1.0 + moneyFlowRatio);

	}

	public static double beta(int period, List<HistoricalQuote> prices, List<HistoricalQuote> prices_bmk) {

		double dailyReturn;
		double dailyReturn_bmk;

		double price;
		double price_bmk;
		double previousPrice = prices.get(0).getAdjClose();
		double previousPrice_bmk = prices_bmk.get(0).getAdjClose();
		double averageDailyReturn = 0;
		double averageDailyReturn_bmk = 0;

		List<Double> dailyReturns = new ArrayList<>(period);
		List<Double> dailyReturns_bmk = new ArrayList<>(period);

		for (int i = 1; i < period; ++i) {
			price = prices.get(i).getAdjClose();

			price_bmk = prices_bmk.get(i).getAdjClose();

			dailyReturn = price / previousPrice - 1;
			dailyReturn_bmk = price_bmk / previousPrice_bmk - 1;

			dailyReturns.add(dailyReturn);
			dailyReturns_bmk.add(dailyReturn_bmk);

			averageDailyReturn += dailyReturn;
			averageDailyReturn_bmk += dailyReturn_bmk;

			previousPrice = price;
			previousPrice_bmk = price_bmk;

		}

		averageDailyReturn /= (period - 1);
		averageDailyReturn_bmk /= (period - 1);

		double volatility_bmk = 0;
		for (double dr_bmk : dailyReturns_bmk) {
			volatility_bmk += Math.pow(dr_bmk - averageDailyReturn_bmk, 2);
		}

		volatility_bmk = volatility_bmk / (period - 1);

		double covariance = 0;
		for (int i = 0; i < period - 1; i++) {

			covariance = Math.pow((dailyReturns.get(i).doubleValue() - averageDailyReturn)
					* (dailyReturns_bmk.get(i).doubleValue() - averageDailyReturn_bmk), 2);
		}

		covariance = covariance / (period - 1);

		return covariance / volatility_bmk;
	}

	public static double correlation(double[] xs, double[] ys) {

		if (xs.length != ys.length) {
			throw new MathException(
					"Arrays not of equal length for the correlation calc ... " + xs.length + " " + ys.length);
		}

		double sx = 0.0;
		double sy = 0.0;
		double sxx = 0.0;
		double syy = 0.0;
		double sxy = 0.0;

		int n = xs.length;

		for (int i = 0; i < n; ++i) {
			double x = xs[i];
			double y = ys[i];

			sx += x;
			sy += y;
			sxx += x * x;
			syy += y * y;
			sxy += x * y;
		}

		// covariation
		double cov = sxy / n - sx * sy / n / n;
		// standard error of x
		double sigmax = Math.sqrt(sxx / n - sx * sx / n / n);
		// standard error of y
		double sigmay = Math.sqrt(syy / n - sy * sy / n / n);

		// correlation is just a normalized covariation
		return cov / sigmax / sigmay;
	}

	public static double acf(int p, int offset, int lag, List<HistoricalQuote> prices) {

		if (p < 0) {
			throw new InvalidPeriodException();
		}

		if (p + offset > prices.size()) {
			throw new InsufficientHistoryException();
		}
		double dailyReturn;
		double dailyReturnLag;

		double[] dailyReturns = new double[p - lag];
		double[] dailyReturnsLag = new double[p - lag];

		int ii = 0;
		for (int i = offset; i < p + offset - lag; ++i) {
			double price = prices.get(i).getAdjClose();
			double previousPrice = prices.get(i + 1).getAdjClose();

			double priceLag = prices.get(i + lag).getAdjClose();
			double previousPriceLag = prices.get(i + 1 + lag).getAdjClose();

			dailyReturn = price / previousPrice - 1;
			dailyReturns[ii] = dailyReturn;

			dailyReturnLag = priceLag / previousPriceLag - 1;
			dailyReturnsLag[ii] = dailyReturnLag;
			++ii;
		}

		return correlation(dailyReturnsLag, dailyReturns);

	}

	public static double rotationIndicator(int longPeriod, int shortPeriod, int offset, int lag,
			List<HistoricalQuote> prices) {

		if (shortPeriod < 0) {
			throw new InvalidPeriodException();
		}

		if (longPeriod + offset > prices.size()) {
			throw new InsufficientHistoryException();
		}

		return acf(shortPeriod + 1, offset, lag, prices) / acf(longPeriod + 1, offset, lag, prices);

	}

	public static double percentIn52Week(SecurityQuoteContainer sqc) {
		return (sqc.getSecurityQuote().getLast() - sqc.getFundamentalQuote().get_52WeekLow())
				/ (sqc.getFundamentalQuote().get_52WeekHigh() - sqc.getFundamentalQuote().get_52WeekLow());
	}

	public static double volumeAverage(List<HistoricalQuote> historicalQuotes, int p, int offset) {

		if (p < 0) {
			throw new InvalidPeriodException();
		}

		if (p + offset > historicalQuotes.size()) {
			throw new InsufficientHistoryException();
		}

		long volume = 0;

		for (int i = offset; i < p + offset; ++i) {
			volume += historicalQuotes.get(i).getVolume();
		}

		return ((double) volume) / p;
	}

	public static double momentum(int p, int offset, List<HistoricalQuote> hq) {

		if (p < 0) {
			throw new InvalidPeriodException();
		}

		if (p + offset > hq.size()) {
			throw new InsufficientHistoryException();
		}

		return hq.get(0 + offset).getAdjClose() - hq.get(p + offset - 1).getAdjClose();
	}

	public static List<Pair<LocalDate, Double>> momentumCurve(int p, List<HistoricalQuote> hq) {

		// P is expected to be at least 2

		if (p < 0) {
			throw new InvalidPeriodException();
		}

		if (p > hq.size()) {
			throw new InsufficientHistoryException();
		}

		List<Pair<LocalDate, Double>> curve = new ArrayList<>(hq.size() - p);

		for (int i = hq.size() - p - 1; i >= 0; --i) {
			curve.add(new Pair<LocalDate, Double>(hq.get(i).getHistoricalDate(),
					hq.get(i).getAdjClose() - hq.get(i + p - 1).getAdjClose()));
		}

		return curve;
	}

}
