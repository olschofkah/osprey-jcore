package com.osprey.math;

import java.util.List;

import com.osprey.securitymaster.HistoricalSecurity;

public final class OspreyMath {

	public static double ema(double sma, int p, List<HistoricalSecurity> prices) {
		int m = 2 / (p + 1); // multiplier
		double ema = sma;

		for (int i = 1; i < p; i++) {
			ema = prices.get(p - i).getClosingPrice() * m + ema * (1 - m);
		}

		return ema;
	}

}
