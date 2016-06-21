package com.osprey.marketdata.batch.processor.lmax;

public class ThrottleEvent {

	public void throttle() {
		try {
			Thread.sleep(100); // TODO Make Configurable
		} catch (InterruptedException e) {
		}
	}

}
