package com.osprey.marketdata.throttle;

public class ThrottleEvent {

	public void throttle() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

}
