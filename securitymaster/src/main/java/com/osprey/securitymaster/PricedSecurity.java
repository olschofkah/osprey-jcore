package com.osprey.securitymaster;

public class PricedSecurity extends Security {

	protected double lastPrice;
	protected double close;
	protected double open;
	protected double bid;
	protected double ask;
	protected double volume;

	public PricedSecurity(String ticker) {
		super(ticker);
	}

	public PricedSecurity(Security s) {
		this(s.ticker);
		this.instrumentType = s.instrumentType;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public double getBid() {
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}

	public double getAsk() {
		return ask;
	}

	public void setAsk(double ask) {
		this.ask = ask;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

}
