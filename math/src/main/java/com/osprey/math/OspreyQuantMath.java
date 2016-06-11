package com.osprey.math;

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

		for (int i = 0; i < p; ++i) {
			ema = prices.get(p - 1 - i).getClosingPrice() * m + ema * (1 - m);
		}

		return ema;
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
			histPrice = prices.get(i).getClosingPrice();
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
			sma1 += prices.get(i).getClosingPrice();
		}

		sma1 /= p;
		return sma1;
	}

}
