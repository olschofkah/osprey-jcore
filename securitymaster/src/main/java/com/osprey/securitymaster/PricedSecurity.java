package com.osprey.securitymaster;

public class PricedSecurity extends Security {

	protected double lastPrice;
	protected double close;
	protected double open;
	protected double bid;
	protected long bidSize;
	protected double ask;
	protected long askSize;
	
	protected long volume;
	protected long averageVolume;

	public PricedSecurity(String ticker) {
		super(ticker);
	}

	public PricedSecurity(Security s) {
		super(s);
	}

	public PricedSecurity(PricedSecurity s) {
		this((Security) s);

		// Set Priced Security vars
		this.close = s.close;
		this.open = s.open;
		this.ask = s.ask;
		this.bid = s.bid;
		this.volume = s.volume;
		this.lastPrice = s.lastPrice;
		this.bidSize = s.bidSize;
		this.askSize = s.askSize;
		this.averageVolume = s.averageVolume;
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

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
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

	public long getBidSize() {
		return bidSize;
	}

	public void setBidSize(long bidSize) {
		this.bidSize = bidSize;
	}

	public long getAskSize() {
		return askSize;
	}

	public void setAskSize(long askSize) {
		this.askSize = askSize;
	}

	public long getAverageVolume() {
		return averageVolume;
	}

	public void setAverageVolume(long averageVolume) {
		this.averageVolume = averageVolume;
	}

}
