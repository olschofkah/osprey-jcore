package com.osprey.marketdata.feed.exception;

public class MarketDataIOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MarketDataIOException() {
		super();
	}

	public MarketDataIOException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public MarketDataIOException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MarketDataIOException(String arg0) {
		super(arg0);
	}

	public MarketDataIOException(Throwable arg0) {
		super(arg0);
	}

}
