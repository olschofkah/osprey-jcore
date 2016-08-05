package com.osprey.marketdata.batch.processor.lmax;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lmax.disruptor.EventHandler;

public class ThrottleEventHandler implements EventHandler<ThrottleEvent> {

	final static Logger logger = LogManager.getLogger(ThrottleEventHandler.class);

	private final AtomicLong throttleCapacity;

	public ThrottleEventHandler(AtomicLong throttleCapacity) {
		this.throttleCapacity = throttleCapacity;
	}

	@Override
	public void onEvent(ThrottleEvent event, long sequence, boolean endOfBatch) throws Exception {
		throttleCapacity.getAndIncrement();
	}

}
