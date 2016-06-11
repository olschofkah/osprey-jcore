package com.osprey.math.result;

public class SMAPair {

	private String ticker;

	private int period1;
	private double sma1;

	private int period2;
	private double sma2;

	public SMAPair() {

	}

	public SMAPair(String ticker, int p1, double sma1, int p2, double sma2) {
		this.ticker = ticker;
		this.period1 = p1;
		this.sma1 = sma1;
		this.period2 = p2;
		this.sma2 = sma2;
	}

	public int getPeriod1() {
		return period1;
	}

	public void setPeriod1(int period1) {
		this.period1 = period1;
	}

	public double getSma1() {
		return sma1;
	}

	public void setSma1(double sma1) {
		this.sma1 = sma1;
	}

	public int getPeriod2() {
		return period2;
	}

	public void setPeriod2(int period2) {
		this.period2 = period2;
	}

	public double getSma2() {
		return sma2;
	}

	public void setSma2(double sma2) {
		this.sma2 = sma2;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

}
