package com.osprey.math;

import java.util.ArrayList;
import java.util.List;

import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.math.exception.InvalidPeriodException;
import com.osprey.math.result.SMAPair;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.OptionType;

public final class OspreyQuantMath {

	/**
	 * Exponential Moving Average
	 * 
	 * @param sma
	 *            Used to seed the initial price in the ema calc. Suggested that
	 *            this is the SMA (Simple Moving Average) but this is not
	 *            required.
	 * @param p
	 *            Period in days
	 * @param prices
	 *            List of historical prices. The first price is expected to be
	 *            the current day.
	 * @return the ema-p
	 */
	public static double ema(double sma, int p, int historicalOffset, List<HistoricalQuote> prices) {
		double alpha = 2.0 / (p + 1.0);
		return ema(sma, p, alpha, historicalOffset, prices);
	}

	public static double ema(int p, int historicalOffset, List<HistoricalQuote> prices) {
		return ema(prices.get(p + historicalOffset - 1).getClose(), p, historicalOffset, prices);
	}

	/**
	 * EMA(t) = EMA(t-1) + smoothing factor * (Price(t) - EMA(t-1)
	 * 
	 * @param sma
	 * @param p
	 * @param alpha
	 *            - scale from 0 to 1
	 * @param offset
	 *            calculate the p ema for n days ago.
	 * @param prices
	 * @return
	 */
	public static double ema(double sma, int p, double alpha, int offset, List<HistoricalQuote> prices) {

		if (p < 2) {
			throw new InvalidPeriodException();
		}

		if (p + offset > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double ema = sma;

		for (int i = p - 2 + offset; i >= offset; --i) {
			// ema(t) = ema(t-1) + alpha * (close(t) - ema(t-1)) where alpha =
			// 2/(1+p)
			// ema(1) = close(1)
			ema = (prices.get(i).getClose() - ema) * alpha + ema;
		}

		return ema;
	}

	/**
	 * MACD = ema (short len) - ema (long len)
	 * 
	 * @param long_len
	 * @param short_len
	 * @param prices
	 * @return
	 */
	public static double MACD(int long_len, int short_len, List<HistoricalQuote> prices, SecurityQuote quote) {

		double sma_long = OspreyQuantMath.sma(long_len, 0, prices);
		double sma_short = OspreyQuantMath.sma(short_len, 0, prices);

		double ema_long = OspreyQuantMath.ema(sma_long, long_len, 0, prices);
		double ema_short = OspreyQuantMath.ema(sma_short, short_len, 0, prices);

		return ema_short - ema_long;

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
	public static double rsi(int p, int offset, List<HistoricalQuote> prices) {

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
				aveLoss += change;
			}
		}

		double rs = aveGain / Math.abs(aveLoss);
		return 100.0 - 100.0 / (1.0 + rs);

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
			histPrice = i == 0 ? quote.getLast() : prices.get(i).getClose();
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

		if (period < 0) {
			throw new InvalidPeriodException();
		}

		if (period > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double dailyReturn;
		double price;
		double previousPrice = prices.get(0).getClose();
		double averageDailyReturn = 0;

		List<Double> dailyReturns = new ArrayList<>(period);

		for (int i = 1; i < period; ++i) {
			price = prices.get(i).getClose();

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

		return Math.pow(volatility / (period - 2), 0.5) * Math.pow(252, 0.5);
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

	private static double CND_A_1 = 0.31938153;
	private static double CND_A_2 = -0.356563782;
	private static double CND_A_3 = 1.781477937;
	private static double CND_A_4 = -1.821255978;
	private static double CND_A_5 = 1.330274429;

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

	public static double beta(int period, List<HistoricalQuote> prices, List<HistoricalQuote> prices_bmk) {

		double dailyReturn;
		double dailyReturn_bmk;

		double price;
		double price_bmk;
		double previousPrice = prices.get(0).getClose();
		double previousPrice_bmk = prices_bmk.get(0).getClose();
		double averageDailyReturn = 0;
		double averageDailyReturn_bmk = 0;

		List<Double> dailyReturns = new ArrayList<>(period);
		List<Double> dailyReturns_bmk = new ArrayList<>(period);

		for (int i = 1; i < period; ++i) {
			price = prices.get(i).getClose();

			price_bmk = prices_bmk.get(i).getClose();

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

}
