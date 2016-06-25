package com.osprey.securitymaster;

import java.util.List;

public class ExtendedFundamentalPricedSecurityWithHistory extends FundamentalPricedSecurity {

	protected double _oVolatility;
	protected double _12ema;
	protected double _15ema;
	protected double _26ema;
	protected double _50ema;
	protected double _200ema;
	protected double _12sma;
	protected double _15sma;
	protected double _26sma;
	protected double _50sma;
	protected double _200sma;

	private List<HistoricalQuote> history;

	public ExtendedFundamentalPricedSecurityWithHistory(String ticker) {
		super(ticker);
	}

	public ExtendedFundamentalPricedSecurityWithHistory(Security s) {
		super(s);
	}

	public ExtendedFundamentalPricedSecurityWithHistory(PricedSecurity s) {
		super(s);
	}

	public ExtendedFundamentalPricedSecurityWithHistory(FundamentalPricedSecurity s) {
		super(s);
	}

	public double get_oVolatility() {
		return _oVolatility;
	}

	public void set_oVolatility(double _oVolatility) {
		this._oVolatility = _oVolatility;
	}

	public double get_12ema() {
		return _12ema;
	}

	public void set_12ema(double _12ema) {
		this._12ema = _12ema;
	}

	public double get_15ema() {
		return _15ema;
	}

	public void set_15ema(double _15ema) {
		this._15ema = _15ema;
	}

	public double get_26ema() {
		return _26ema;
	}

	public void set_26ema(double _26ema) {
		this._26ema = _26ema;
	}

	public double get_50ema() {
		return _50ema;
	}

	public void set_50ema(double _50ema) {
		this._50ema = _50ema;
	}

	public double get_200ema() {
		return _200ema;
	}

	public void set_200ema(double _200ema) {
		this._200ema = _200ema;
	}

	public double get_12sma() {
		return _12sma;
	}

	public void set_12sma(double _12sma) {
		this._12sma = _12sma;
	}

	public double get_15sma() {
		return _15sma;
	}

	public void set_15sma(double _15sma) {
		this._15sma = _15sma;
	}

	public double get_26sma() {
		return _26sma;
	}

	public void set_26sma(double _26sma) {
		this._26sma = _26sma;
	}

	public double get_50sma() {
		return _50sma;
	}

	public void set_50sma(double _50sma) {
		this._50sma = _50sma;
	}

	public double get_200sma() {
		return _200sma;
	}

	public void set_200sma(double _200sma) {
		this._200sma = _200sma;
	}

	public List<HistoricalQuote> getHistory() {
		return history;
	}

	public void setHistory(List<HistoricalQuote> history) {
		this.history = history;
	}

}
