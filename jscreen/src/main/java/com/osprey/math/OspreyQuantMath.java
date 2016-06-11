package com.osprey.math;

import java.util.List;

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
		int m = 2 / (p + 1); // multiplier
		double ema = sma;

		for (int i = 1; i < p; ++i) {
			ema = prices.get(p - i).getClosingPrice() * m + ema * (1 - m);
		}

		return ema;
	}

}
