package com.osprey.securitymaster.secondary;

import com.osprey.securitymaster.HistoricalQuote;

public class HistoricalLossQuote extends HistoricalQuote {

	private double gainLossForPeriod;

	public HistoricalLossQuote(HistoricalQuote q0, HistoricalQuote q1) {
		super(q0.getKey().getSymbol(), q0.getHistoricalDate());

		if (q0.getHistoricalDate().isBefore(q1.getHistoricalDate())) {
			throw new RuntimeException("Bad Historial Gain/Loss Quote Construction");
		}

		double gl = q0.getClose() - q1.getClose();
		this.gainLossForPeriod = gl >= 0 ? 0 : gl * -1;

		setHigh(q0.getHigh());
		setLow(q0.getLow());
		setOpen(q0.getOpen());
		setVolume(q0.getVolume());
	}

	public double getClose() {
		return gainLossForPeriod;
	}

	public double getAdjClose() {
		return gainLossForPeriod;
	}

}
