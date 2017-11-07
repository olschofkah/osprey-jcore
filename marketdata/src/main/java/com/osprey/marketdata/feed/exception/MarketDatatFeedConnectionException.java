package com.osprey.marketdata.feed.exception;

public class MarketDatatFeedConnectionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8412853160040819495L;

	public MarketDatatFeedConnectionException() {
		super();
	}

	public MarketDatatFeedConnectionException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public MarketDatatFeedConnectionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MarketDatatFeedConnectionException(String arg0) {
		super(arg0);
	}

	public MarketDatatFeedConnectionException(Throwable arg0) {
		super(arg0);
	}

}
