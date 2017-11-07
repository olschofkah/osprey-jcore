package com.osprey.marketdata.feed.exception;

public class MarketDataNotAvailableException extends Exception  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MarketDataNotAvailableException() {
		super();
	}

	public MarketDataNotAvailableException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public MarketDataNotAvailableException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MarketDataNotAvailableException(String arg0) {
		super(arg0);
	}

	public MarketDataNotAvailableException(Throwable arg0) {
		super(arg0);
	}

}
