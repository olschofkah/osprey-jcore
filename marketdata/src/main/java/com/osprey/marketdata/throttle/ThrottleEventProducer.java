package com.osprey.marketdata.throttle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lmax.disruptor.RingBuffer;

public class ThrottleEventProducer {
	final static Logger logger = LogManager.getLogger(ThrottleEventProducer.class);

	private final RingBuffer<ThrottleEvent> ringBuffer;

	public ThrottleEventProducer(RingBuffer<ThrottleEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void load() {
		long sequence = ringBuffer.next();
		try {
			ThrottleEvent event = ringBuffer.get(sequence);
			event.throttle();
		} finally {
			ringBuffer.publish(sequence);
		}
	}
}