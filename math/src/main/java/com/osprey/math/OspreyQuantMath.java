package com.osprey.math;

import java.util.ArrayList;
import java.util.List;

import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.math.exception.InvalidPeriodException;
import com.osprey.math.result.SMAPair;
import com.osprey.securitymaster.HistoricalSecurity;

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
	public static double ema(double sma, int p, List<HistoricalSecurity> prices) {

		if (p < 0) {
			throw new InvalidPeriodException();
		}

		if (p > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double m = 2.0 / (p + 1.0); // multiplier
		double ema = sma;

		for (int i = 1; i < p; ++i) {
			ema = prices.get(i).getClose() * m + ema * (1 - m);
		}

		return ema;
	}
	
	
	/**EMA(t) = EMA(t-1) + smoothing factor * (Price(t) - EMA(t-1)
	 * @param sma
	 * @param p
	 * @param alpha - scale from 1 to 10
	 * @param prices
	 * @return
	 */
	public static double ema_smooth(double sma, int p, double alpha, List<HistoricalSecurity> prices) {

		if (p < 0) {
			throw new InvalidPeriodException();
		}

		if (p > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double ema = sma;

		for (int i = 1; i < p; ++i) {
			ema = prices.get(i).getClose() * alpha/10 + ema * (1 - alpha/10);
		}

		return ema;
	}

	
	/**
	 * MACD = ema (short len) - ema (long len)
	 * @param long_len
	 * @param short_len
	 * @param prices
	 * @return
	 */
	public static double MACD(int long_len, int short_len, List<HistoricalSecurity> prices) {

 		
		
		double sma_long = OspreyQuantMath.sma(long_len, prices);
		double sma_short = OspreyQuantMath.sma(short_len, prices);
		
		double ema_long = OspreyQuantMath.ema(sma_long, long_len, prices);
		double ema_short = OspreyQuantMath.ema(sma_short, short_len, prices);
				
		 return ema_short - ema_long; 

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
	public static SMAPair smaPair(int p1, int p2, List<HistoricalSecurity> prices) {

		if (p1 < 0 || p2 < 0) {
			throw new InvalidPeriodException();
		}

		int r = p1 > p2 ? p1 : p2;

		if (r > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double sma1 = 0;
		double sma2 = 0;

		double histPrice = 0;
		for (int i = 0; i < r; ++i) {
			histPrice = prices.get(i).getClose();
			if (i <= p1) {
				sma1 += histPrice;
			}
			if (i <= p2) {
				sma2 += histPrice;
			}
		}

		sma1 /= p1;
		sma2 /= p2;

		return new SMAPair(prices.get(0).getTicker(), p1, sma1, p2, sma2);
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
	public static double sma(int p, List<HistoricalSecurity> prices) {

		if (p < 0) {
			throw new InvalidPeriodException();
		}

		if (p > prices.size()) {
			throw new InsufficientHistoryException();
		}

		double sma1 = 0;

		for (int i = 0; i < p; ++i) {
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
	public static double volatility(int period, List<HistoricalSecurity> prices) {

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

	public static double Beta(int period, List<HistoricalSecurity> prices, List<HistoricalSecurity> prices_bmk) {

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

}
